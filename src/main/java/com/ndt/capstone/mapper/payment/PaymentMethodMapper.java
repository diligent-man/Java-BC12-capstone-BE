package com.ndt.capstone.mapper.payment;

import com.ndt.capstone.entity.PaymentMethodEntity;
import com.ndt.capstone.enums.payment.PaymentMethod;
import com.ndt.capstone.dto.payment.PaymentMethodDTO;


public class PaymentMethodMapper {
    private PaymentMethodMapper() {
    }


    public static PaymentMethodDTO toDTO(PaymentMethodEntity obj) {
        if (obj == null)
            return null;

        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setName(PaymentMethod.fromName(obj.getName()).getAlterName());
        return dto;
    }
}
