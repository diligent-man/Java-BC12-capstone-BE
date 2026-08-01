package com.ndt.capstone.service;

import java.util.Optional;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.payload.request.auth.SignupRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public String doLogin(LoginRequest request) {
        String accessToken = null;
        Optional<UserEntity> opUser = userRepo.findByEmail(request.getEmail());

        if (opUser.isPresent()) {
            UserEntity user = opUser.get();

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                accessToken = jwtService.genAccessToken(UserDto.fromEntity(user));
            }
        }
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
            userRepo.save(newUser);

            //Bắn sự kiện lên Kafka để hệ thống tự gửi mail sau
            kafkaProducerService.sendRegistrationEmailEvent(newUser.getEmail());
        }

    }
}
