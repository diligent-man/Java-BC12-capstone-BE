package com.ndt.capstone.service.contract;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.dto.order.OrderHistoryDTO;
import com.ndt.capstone.payload.request.payment.CheckoutRequest;
import com.ndt.capstone.payload.request.payment.OrderConfirmRequest;
import com.ndt.capstone.payload.response.PageResponse;


public interface OrderService {
    CheckoutDTO processCheckout(CheckoutRequest req, Long userId);


    void confirmPayment(OrderConfirmRequest req);

    PageResponse<OrderHistoryDTO> getOrderHistory(Long userId, int page, int size);
}
