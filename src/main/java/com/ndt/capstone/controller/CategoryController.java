package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.CategoryService;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<ApiResponse> getCategories() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(categoryService.getAll())
                .build()
        );
    }
}
