package com.ndt.capstone.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDTO {
    private Long orderId;           // Mã đơn hàng vừa tạo (VD: 101)
    private String qrUrl;           // Link ảnh VietQR động
    private BigDecimal amount;      // Số tiền cần chuyển
    private String transferContent; // Nội dung chuyển khoản (VD: "DH101")
}