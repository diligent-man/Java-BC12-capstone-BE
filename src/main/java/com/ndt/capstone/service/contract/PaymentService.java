package com.ndt.capstone.service.contract;


import java.util.List;


import com.ndt.capstone.dto.payment.PaymentMethodDTO;


public interface PaymentService {
    List<PaymentMethodDTO> getPaymentMethods();
}
