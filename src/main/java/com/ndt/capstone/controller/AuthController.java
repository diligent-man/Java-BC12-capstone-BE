package com.ndt.capstone.controller;

import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.enums.exception.AuthErrMsg;
import com.ndt.capstone.service.contract.AuthService;

import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.request.auth.SignupRequest;

import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.response.auth.AuthResponse;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authenService;


    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> login(
        @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(AuthErrMsg.SIGNIN_SUCCESS.getErrorMsg())
                .data(AuthResponse.builder().token(authenService.doSignIn(request)).build())
                .build()
        );
    }


    @PostMapping("/signout")
    public ResponseEntity<ApiResponse> logout(
        @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        authenService.doSignOut(token);

        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(AuthErrMsg.SIGNOUT_SUCCESS.getErrorMsg())
                .build()
        );
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(
        @Valid @RequestBody SignupRequest signupRequest
    ) {
        authenService.doSignUp(signupRequest);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(AuthErrMsg.SIGNUP_SUCCESS.getErrorMsg())
                .build()
        );
    }


    @GetMapping("/check-session")
    public ResponseEntity<ApiResponse> checkSession() {
        // Nếu request lọt qua được AuthFilter → session Redis còn sống → trả 200
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message(AuthErrMsg.SIGNIN_SUCCESS.getErrorMsg())
                .build()
        );
    }
}
