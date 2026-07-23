package com.ndt.capstone.controller;

import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.enums.exception.JwtError;
import com.ndt.capstone.payload.request.jwt.GenTokenRequest;
import com.ndt.capstone.payload.response.ApiResponse;
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
        return ResponseEntity.ok(
            ApiResponse.builder()
                .code("200")
                .status(JwtError.KEY_CREATION_SUCCESS.getMessage())
                .data(JwtService.genSecretKey())
                .build()
        );
    }


    @PostMapping("/gen-token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody GenTokenRequest request) {
        System.out.println(request);
        UserDto user = userService.getUserByEmail(request.getEmail());
        System.out.println(user);

        return ResponseEntity.ok(
            ApiResponse.builder()
                .code("200")
                .status(JwtError.TOKEN_CREATION_SUCCESS.getMessage())
                .data(jwtService.genAccessToken(user))
                .build()
        );
    }
}
