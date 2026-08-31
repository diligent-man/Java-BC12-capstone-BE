package com.ndt.capstone.controller;

import com.ndt.capstone.payload.request.auth.SignupRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.service.AuthService;
import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.response.auth.AuthResponse;


@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
        LoginRequest request
    ) {
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

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestHeader("Authorization") String authHeader
    ) {
        // Cắt bỏ "Bearer " (7 ký tự đầu)
        String token = authHeader.substring(7);
        authenService.doLogout(token);

        ApiResponse response = ApiResponse.builder()
                .code(AuthError.SUCCESS.getHttpStatus().toString())
                .status(AuthError.SUCCESS.toString())
                .data("Đăng xuất thành công")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest){
        authenService.doSignUp(signupRequest);
        return ResponseEntity.ok("Đăng ký thành công, vui lòng kiểm tra mail");
    }
}
