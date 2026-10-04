package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.resp.ApiResponse;
import com.ndt.capstone.service.contract.ColorService;


@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;


    @GetMapping
    public ResponseEntity<ApiResponse> getColors() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(colorService.getAll())
                .build()
        );
    }
}
