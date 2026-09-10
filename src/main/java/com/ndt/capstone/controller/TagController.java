package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


import com.ndt.capstone.service.contract.TagService;
import com.ndt.capstone.payload.response.ApiResponse;


@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;


    @GetMapping
    public ResponseEntity<ApiResponse> getTags() {
        return ResponseEntity.ok(
            ApiResponse
                .builder()
                .data(tagService.getAll())
                .build()
        );
    }
}
