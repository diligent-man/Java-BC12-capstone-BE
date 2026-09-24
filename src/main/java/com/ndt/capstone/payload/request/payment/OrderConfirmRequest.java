package com.ndt.capstone.payload.request.payment;

import jakarta.validation.constraints.Positive;


import lombok.Data;


@Data
public class OrderConfirmRequest {
    @Positive
    Long orderId;
}
