package com.ndt.capstone.controller;

import com.ndt.capstone.payload.request.payment.OrderConfirmRequest;
import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


import com.ndt.capstone.payload.response.ApiResponse;
import com.ndt.capstone.service.contract.OrderService;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;


@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(
        @Valid @RequestBody CheckoutRequest req,
        Authentication auth
    ) {
        Long userId = Long.parseLong(auth.getName());
        return ResponseEntity.ok(
            ApiResponse.builder()
                .data(orderService.processCheckout(req, userId))
                .build()
        );
    }


    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse> confirmPayment(
        @Valid @RequestBody OrderConfirmRequest req
    ) {
        orderService.confirmPayment(req);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }
}
