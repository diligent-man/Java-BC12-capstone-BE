package com.ndt.capstone.payload.request.payment;

import java.util.List;
import java.math.BigDecimal;


import jakarta.validation.Valid;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;


import lombok.Data;


@Data
public class CheckoutRequest {
    @Valid
    @NotNull
    private BillingDetailsRequest billing;

    @NotEmpty
    private List<@Valid OrderItemRequest> items;


    @NotBlank
    @Size(max = 50)
    private String paymentMethodName;

    @Nullable
    private String note;

    @NotNull
    private BigDecimal totalAmount;
}
