package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.CountryService;

    
@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllCountries() {
        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(countryService.getAllCountries())
                .build()
        );
    }
}
