package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.service.AuthService;
import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.response.AuthResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenController {
    private final AuthService authenService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
        @RequestBody LoginRequest request
    ) {
        String accessToken = authenService.doLogin(request);

        AuthResponse authResponse = new AuthResponse(accessToken);

        ApiResponse response = ApiResponse.builder()
            .code(AuthError.SUCCESS.getHttpStatus().toString())
            .status(AuthError.SUCCESS.toString())
            .data(authResponse)
            .build();
        return ResponseEntity.ok(response);
    }
}
