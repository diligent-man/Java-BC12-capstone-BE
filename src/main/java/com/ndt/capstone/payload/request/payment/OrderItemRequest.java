package com.ndt.capstone.payload.request.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class OrderItemRequest {
    @NotNull(message = "skuVariant không được để trống")
    private Long skuVariant;
    @NotNull(message = "quantity không được để trống")
    private Integer quantity;
    @NotNull(message = "price không được để trống")
    private BigDecimal price;
}