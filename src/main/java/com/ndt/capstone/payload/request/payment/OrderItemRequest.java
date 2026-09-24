package com.ndt.capstone.payload.request.payment;

import java.math.BigDecimal;


import jakarta.validation.constraints.*;


import lombok.Data;


@Data
public class OrderItemRequest {
    @NotNull
    private Long sku;

    @NotNull
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
}
