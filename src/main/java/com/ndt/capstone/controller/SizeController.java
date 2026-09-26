package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.SizeService;


@RestController
@RequestMapping("/size")
@RequiredArgsConstructor
public class SizeController {
    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getSizes() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(sizeService.getAll())
                .build()
        );
    }
}
