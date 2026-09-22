package com.ndt.capstone.controller;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;
import com.ndt.capstone.payload.response.ApiResponse;
 // Import đúng đường dẫn CheckoutDTO của bạn
import com.ndt.capstone.service.contract.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(
            @RequestBody CheckoutRequest request,
            Authentication authentication
    ) {
        // 1. Lấy userId của người đang đăng nhập (do AuthFilter đã xác thực từ JWT token)
        Long userId = ((Number) authentication.getPrincipal()).longValue();

        // 2. Gọi OrderService xử lý lưu đơn hàng và sinh link VietQR
        CheckoutDTO result = orderService.processCheckout(request, userId);

        // 3. Trả về đúng chuẩn ApiResponse của dự án
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(result)
                        .build()
        );
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<ApiResponse> confirmPayment(
            @PathVariable Long orderId
    ) throws Exception {
        orderService.confirmPayment(orderId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Xac nhan thanh toan thanh cong!")
                        .build()
        );
    }
}