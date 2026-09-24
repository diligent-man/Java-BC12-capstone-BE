package com.ndt.capstone.service.contract;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;
import com.ndt.capstone.payload.request.payment.OrderConfirmRequest;


public interface OrderService {
    CheckoutDTO processCheckout(CheckoutRequest req, Long userId);


    void confirmPayment(OrderConfirmRequest req);
}
