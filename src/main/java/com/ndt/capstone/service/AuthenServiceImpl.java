package com.ndt.capstone.service;

import java.util.Optional;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.entity.RoleEntity;
import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.payload.request.auth.SignupRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Claims;
import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.payload.request.auth.LoginRequest;


@Service
@RequiredArgsConstructor
public class AuthenServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Value("${jwt.expiration}")
    private long jwtExpiration;



    @Override
    public String doLogin(LoginRequest request) {
        String email = request.getEmail();

        //đầu tiên lấy locktype rồi kiểm tra xem email này có đang bị khóa hay không
        String lockType = loginAttemptService.getLockType( email); //Lụm ra locktype
        if("PERMANENT".equals(lockType)){
            throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
        }
        if ("TEMP".equals(lockType)) {
            throw new AuthException(AuthError.ACCOUNT_TEMP_LOCKED);
        }

        //Nếu không bị dính khóa, tiếp tục luồng đăng nhập
        //B1 lấy thông tin user trong DB
        Optional<UserEntity> opUser = userRepo.findByEmail(email);
        if (opUser.isEmpty()) { //*Nếu email sai
            // nếu nhập sai email nó vẫn tăng 1 lần sai, để chống việc mấy thg hacker nó xài tool dò email
            loginAttemptService.recordFailedAttempt(email);
            throw new AuthException(AuthError.INVALID_CREDENTIALS);
        }
        UserEntity user = opUser.get();
        // lấy được user thì kiểm tra ngay status trong DB ( phòng trường hợp redis bị crash ất key)
        if ("PERMANENTLY_LOCKED".equals(user.getStatus())) {
            throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
        }


        //*Nếu mk sai
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            //kích hoạt hàm recordFailedAttempt để tăng lên 1 lần sai
            int remaining = loginAttemptService.recordFailedAttempt(email);

            // Sau khi tăng lên thì kiểm tra lại xem có bị khoá chưa,
            // vì khi hàm recordFailedAttempt được kích hoạt, nếu thỏa >3 nhập sai, nó sẽ tạo ra các key để block log
            String newLockType = loginAttemptService.getLockType(email); //lấy key để kiểm tra
            if ("PERMANENT".equals(newLockType)) {
                throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
            }

            if ("TEMP".equals(newLockType)) {
                throw new AuthException(AuthError.ACCOUNT_TEMP_LOCKED);
            }

            // Chưa bị khoá → thông báo sai MK (remaining chứa số lần còn lại)
            System.out.println("so lan con lại " + remaining);
            throw new AuthException(AuthError.INVALID_CREDENTIALS);

        }

        //*Nếu MK đúng -> kiểm tra single session để xem có trình duyệt nào khác đang xài không
        String existingSession = loginAttemptService.getActiveSession(email);

        if (existingSession != null) {
            // Đã có session đang active ở trình duyệt khác → CHẶN
            throw new AuthException(AuthError.ACCOUNT_ACTIVE_SESSION);
        }

        // ========== BƯỚC 5: Đăng nhập thành công ==========
        // Reset bộ đếm sai
        loginAttemptService.resetFailedAttempts(email);

        // Tạo JWT token
        String accessToken = jwtService.genAccessToken(UserDto.fromEntity(user));

        // Lưu session vào Redis (TTL = thời gian sống JWT)
        loginAttemptService.saveSession(email, accessToken, jwtExpiration);

        return accessToken;
    }


    @Transactional
    @Override
    public void doSignUp(SignupRequest request) {
        Optional<UserEntity> opUser = userRepo.findByEmail(request.getEmail());
        if(opUser.isEmpty()){ //neu kiemtra khong thay user
            UserEntity newUser = new UserEntity();
            RoleEntity role = new RoleEntity();


            String encodedPassword = passwordEncoder.encode(request.getPassword());
            newUser.setEmail(request.getEmail());

            newUser.setPassword(encodedPassword);

            newUser.setFullName(request.getFullName());

            newUser.setStatus("ACTIVE");

            role.setId(3);
            newUser.setRole(role);
            
            userRepo.save(newUser);

            kafkaProducerService.sendRegistrationEmailEvent(request.getEmail());

        } else {
            System.out.println("email da ton tai");
        }
    }

    @Override
    public void doLogout(String token) {
        // Parse JWT lấy email
        Claims claims = jwtService.extractClaims(token);
        String email = claims.get("email", String.class);

        // Xoá session trong Redis
        loginAttemptService.removeSession(email);
    }


}
