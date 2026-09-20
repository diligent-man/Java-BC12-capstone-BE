package com.ndt.capstone.service.contract;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;

public interface OrderService {
    CheckoutDTO processCheckout(CheckoutRequest request, Long userId);
    void confirmPayment(Long orderId);
}