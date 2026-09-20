package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.AccountService;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AccountService accountService;


    @PostMapping("/account/lock/{id}")
    public ResponseEntity<ApiResponse> lock(
        @PathVariable Long id
    ) {
        accountService.lockAccount(id);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message("Lock account successfully")
                .build()
        );
    }


    @PostMapping("/account/unlock/{id}")
    public ResponseEntity<ApiResponse> unlockAccount(
        @PathVariable Long id
    ) {
        accountService.unlockAccount(id);
        return ResponseEntity.ok(
            ApiResponse.builder()
                .message("Unlock account successfully")
                .build()
        );
    }
}
