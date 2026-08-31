package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;

import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ndt.capstone.service.LoginAttemptService;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.enums.exception.AuthError;


@CrossOrigin
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoginAttemptService loginAttemptService;

    @PostMapping("/unlock/{email}")
    public ResponseEntity<ApiResponse> unlockAccount(@PathVariable String email) {
        loginAttemptService.unlockAccount(email);

        ApiResponse response = ApiResponse.builder()
                .code(AuthError.SUCCESS.getHttpStatus().toString())
                .status(AuthError.SUCCESS.toString())
                .data("Mở khoá tài khoản " + email + " thành công")
                .build();
        return ResponseEntity.ok(response);
    }
}