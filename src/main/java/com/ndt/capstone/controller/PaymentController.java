package com.ndt.capstone.controller;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.PaymentService;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @GetMapping("/methods")
    public ResponseEntity<ApiResponse> getPaymentMethods() {
        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(paymentService.getPaymentMethods())
                .build()
        );
    }
}
