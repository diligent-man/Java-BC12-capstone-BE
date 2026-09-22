package com.ndt.capstone.payload.request.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
@Data
public class CheckoutRequest {
    @Valid
    @NotNull(message = "billing details không được để trống")
    private BillingDetailsRequest billing;
    @NotEmpty(message = "items không được rỗng")
    private List<OrderItemRequest> items;
    @NotNull(message = "paymentMethodId không được để trống")
    private Integer paymentMethodId;
    private String note;
    @NotNull(message = "totalAmount không được để trống")
    private BigDecimal totalAmount;
}