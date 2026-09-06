package com.ndt.capstone.controller;

import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.enums.exception.JwtErrMsg;
import com.ndt.capstone.payload.request.jwt.GenTokenRequest;
import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.payload.response.jwt.GenKeyResponse;
import com.ndt.capstone.payload.response.jwt.GenTokenResponse;
import com.ndt.capstone.service.UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.service.JwtService;


@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JwtController {
    private final UserService userService;

    private final JwtService jwtService;


    @GetMapping("/gen-key")
    public ResponseEntity<ApiResponse> generateKey() {
        GenKeyResponse genKeyResponse = GenKeyResponse.builder().secreteKey(JwtService.genSecretKey()).build();
        return ResponseEntity.ok(
            ApiResponse.builder()
                .code(String.valueOf(JwtErrMsg.KEY_CREATION_SUCCESS.getHttpStatus().value()))
                .message(JwtErrMsg.KEY_CREATION_SUCCESS.getErrorMsg())
                .data(genKeyResponse)
                .build()
        );
    }


    @PostMapping("/gen-token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody GenTokenRequest request) {
        UserDto user = userService.getUserByEmail(request.getEmail());

        GenTokenResponse genTokenResponse = GenTokenResponse.builder()
            .token(jwtService.genAccessToken(user))
            .build();

        return ResponseEntity.ok(
            ApiResponse.builder()
                .code(String.valueOf(JwtErrMsg.TOKEN_CREATION_SUCCESS.getHttpStatus().value()))
                .message(JwtErrMsg.TOKEN_CREATION_SUCCESS.getErrorMsg())
                .data(genTokenResponse)
                .build()
        );
    }
}
