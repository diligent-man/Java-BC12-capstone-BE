package com.ndt.capstone.service;

import java.util.Optional;


import com.ndt.capstone.dto.UserDto;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.payload.request.auth.LoginRequest;


@Service
@RequiredArgsConstructor
public class AuthenServiceImpl implements AuthService {
    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


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
}
