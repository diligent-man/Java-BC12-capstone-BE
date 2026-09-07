package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.BrandService;


@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;


    @GetMapping
    public ResponseEntity<ApiResponse> getBrands() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(brandService.getAll())
                .build()
        );
    }
}
