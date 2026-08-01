package com.ndt.capstone.controller;

import com.ndt.capstone.payload.request.auth.SignupRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.service.AuthService;
import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.response.auth.AuthResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authenService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        String accessToken = authenService.doLogin(request);

        AuthResponse authResponse = AuthResponse.builder()
            .accessToken(accessToken)
            .build();

        ApiResponse response = ApiResponse.builder()
            .code(AuthError.SUCCESS.getHttpStatus().toString())
            .status(AuthError.SUCCESS.toString())
            .data(authResponse)
            .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        authenService.doSignup(request);

        return ResponseEntity.ok("Đăng ký thành công. Vui lòng kiểm tra email");
    }
}
