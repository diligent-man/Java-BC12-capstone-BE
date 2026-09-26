package com.ndt.capstone.controller;

import com.ndt.capstone.service.contract.ColorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ndt.capstone.payload.response.ApiResponse;


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
