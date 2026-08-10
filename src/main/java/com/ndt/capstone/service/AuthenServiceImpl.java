package com.ndt.capstone.service;

import java.util.Optional;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.entity.RoleEntity;
import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.payload.request.auth.SignupRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.payload.request.auth.LoginRequest;
import org.springframework.transaction.annotation.Transactional;


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

        // 1. Kiểm tra khoá tài khoản
        String lockType = loginAttemptService.getLockType(email);

        if ("PERMANENT".equals(lockType)) {
            throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
        }

        if ("TEMP".equals(lockType)) {
            throw new AuthException(AuthError.ACCOUNT_TEMP_LOCKED);
        }

        // 2.Tìm user trong DB
        Optional<UserEntity> opUser = userRepo.findByEmail(email);

        if (opUser.isEmpty()) {
            // Vẫn ghi nhận lần sai (chống brute-force dò email)
            loginAttemptService.recordFailedAttempt(email);
            throw new AuthException(AuthError.INVALID_CREDENTIALS);
        }

        UserEntity user = opUser.get();
        if("PERMANETLY_LOCKED".equals(user.getStatus())){
            throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
        }

        // 3. Kiểm tra mật khẩu
        // Nếu mk sai
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            int remaining = loginAttemptService.recordFailedAttempt(email);

            // Sau khi record, kiểm tra lại xem có bị khoá chưa
            String newLockType = loginAttemptService.getLockType(email);

            if ("PERMANENT".equals(newLockType)) {
                throw new AuthException(AuthError.ACCOUNT_PERMANENTLY_LOCKED);
            }

            if ("TEMP".equals(newLockType)) {
                throw new AuthException(AuthError.ACCOUNT_TEMP_LOCKED);
            }

            // Chưa bị khoá → thông báo sai MK (remaining chứa số lần còn lại)
            throw new AuthException(AuthError.INVALID_CREDENTIALS);
        }

        // 4. Nếu mật khẩu đúng → Kiểm tra Single Session
        String existingSession = loginAttemptService.getActiveSession(email);

        if (existingSession != null) {
            // Đã có session đang active ở trình duyệt khác → CHẶN
            throw new AuthException(AuthError.ACCOUNT_ACTIVE_SESSION);
        }

        // 5. Đăng nhập thành công
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
    public void doSignup(SignupRequest request) {
        //Kiểm tra email có trùng hay không
        Optional<UserEntity> opUser = userRepo.findByEmail(request.getEmail());
        if (opUser.isPresent()) {
            System.out.println("email đã tồn tại");
        } else {
            //Nếu không thì lưu vào database
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            UserEntity newUser = new UserEntity();
            newUser.setEmail(request.getEmail());
            newUser.setPassword(encodedPassword);
            newUser.setFullName(request.getFullname());
            RoleEntity role = new RoleEntity();
            role.setId(3);
            newUser.setRole(role);
            newUser.setStatus("ACTIVE");
            userRepo.save(newUser);

            //Bắn sự kiện lên Kafka để hệ thống tự gửi mail sau
            kafkaProducerService.sendRegistrationEmailEvent(newUser.getEmail());
        }

    }

    @Override
    public void doLogout(String token) {
        // Parse JWT lấy email
        Claims claims = jwtService.extractClaims(token);
        String email = claims.get("email", String.class);

        if (email != null) {
            // Xóa session trong Redis, giải phóng tài khoản để đăng nhập ở nơi khác
            loginAttemptService.removeSession(email);
        }
    }
}
