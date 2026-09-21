package com.ndt.capstone.service;

import java.util.Objects;

import jakarta.transaction.Transactional;


import io.jsonwebtoken.Claims;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.service.contract.AuthService;


import com.ndt.capstone.mapper.UserMapper;
import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.exception.user.UserException;

import com.ndt.capstone.enums.exception.UserErrMsg;
import com.ndt.capstone.enums.exception.AuthErrMsg;
import com.ndt.capstone.enums.account.AccountStatus;

import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.request.auth.SignupRequest;


@Service
public class AuthenServiceImpl implements AuthService {
    private final UserRepository userRepo;

    private final JwtServiceImpl jwtService;

    private final KafkaProducerService kafkaProducerService;

    private final LoginAttemptServiceImpl loginAttemptService;

    private final PasswordEncoder passwordEncoder;

    private final long rememberMeExpiration;


    public AuthenServiceImpl(
        UserRepository userRepo,
        JwtServiceImpl jwtService,
        KafkaProducerService kafkaProducerService,
        LoginAttemptServiceImpl loginAttemptService,
        PasswordEncoder passwordEncoder,
        @Value("${auth.remember-me-expiration:86400000}") long rememberMeExpiration
    ) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.kafkaProducerService = kafkaProducerService;
        this.loginAttemptService = loginAttemptService;
        this.passwordEncoder = passwordEncoder;
        this.rememberMeExpiration = rememberMeExpiration;
    }


    @Override
    public String doSignIn(LoginRequest req) {
        String email = req.getEmail();

        // email check
        loginAttemptService.checkLock(email);
        UserEntity user = userRepo
            .findByEmail(email)
            .orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));

        // user account status check
        if (AccountStatus.LOCKED.name().equals(user.getStatus())) {
            throw new AuthException(AuthErrMsg.ACCOUNT_PERMANENTLY_LOCKED);
        }

        // password check
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            long remainingAttempts = loginAttemptService.recordFailedAttempt(email);

            if (remainingAttempts == 0) {
                user.setStatus(AccountStatus.LOCKED.name());
                userRepo.save(user);
            }

            loginAttemptService.checkLock(email);
            throw new AuthException(
                AuthErrMsg.INVALID_CREDENTIALS,
                String.format("%s (remaining attempt: %d)", AuthErrMsg.INVALID_CREDENTIALS.getErrorMsg(), remainingAttempts)
            );
        }


        // single session check
        if (Objects.nonNull(loginAttemptService.getActiveSession(email)))
            throw new AuthException(AuthErrMsg.ACCOUNT_ACTIVE_SESSION);

        loginAttemptService.resetFailedAttempts(email);

        long expiration = req.isRememberMe() ? rememberMeExpiration : jwtService.getExpiration();
        String token = jwtService.genAccessToken(UserDto.fromEntity(user), expiration);
        loginAttemptService.saveSession(email, token, expiration);
        return token;
    }


    @Override
    public void doSignOut(String token) {
        Claims claims = jwtService.extractClaims(token);
        String email = claims.get("email", String.class);
        loginAttemptService.removeSession(email);
    }



    @Override
    @Transactional
    public void doSignUp(SignupRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new UserException(UserErrMsg.EXISTED);

        userRepo.save(UserMapper.toEntity(req, passwordEncoder));
        kafkaProducerService.sendRegistrationEmailEvent(req.getEmail());
    }
}
