package com.ndt.capstone.mapper;

import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.ndt.capstone.entity.*;

import com.ndt.capstone.utils.PhoneUtils;
import com.ndt.capstone.exception.payment.PaymentException;

import com.ndt.capstone.enums.payment.PaymentMethod;
import com.ndt.capstone.enums.exception.PaymentErrMsg;


@Component
public class OrderPaymentPayloadMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public String buildPayload(
        OrderEntity order,
        BillingDetailsEntity billing,
        List<OrderVariantEntity> orderItems
    ) {
        // 1. Thông tin khách hàng
        String phone = billing.getPhone();
        if (phone != null)
            phone = PhoneUtils.formatPhoneNumber(phone);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", billing.getFirstName() + " " + billing.getLastName());
        customerMap.put("email", billing.getEmail());
        customerMap.put("phone", phone);
        customerMap.put("address", billing.getAddress());
        customerMap.put("town", billing.getTown());
        customerMap.put("country", billing.getCountry().getName());

        // 2. Danh sách sản phẩm
        List<Map<String, Object>> itemsList = new ArrayList<>();
        for (OrderVariantEntity ov : orderItems) {
            ProductVariantEntity variant = ov.getVariant();
            Map<String, Object> itemMap = new HashMap<>();

            itemMap.put("productName", variant.getProduct().getName());
            itemMap.put("color", variant.getColor().getName());
            itemMap.put("size", variant.getSize().getName());
            itemMap.put("quantity", ov.getQuantity());
            itemMap.put("unitPrice", ov.getPrice());

            itemsList.add(itemMap);
        }

        // 3. Ngày giao dự kiến
        LocalDateTime orderDate = order.getCreateDate();
        LocalDateTime estimatedDelivery = orderDate.plusDays(2);  // temp hard-coded

        // Format ngày đặt: yyyy-MM-dd HH:mm:ss (bỏ chữ T)
        String formattedOrderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Format ngày giao dự kiến: chỉ lấy ngày yyyy-MM-dd (bỏ giờ)
        String formattedEstimatedDelivery = estimatedDelivery.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // lấy ra ten của phương thức thanh toán
        String paymentMethodName = "Không xác định";
        if (order.getPayment() != null) {
            paymentMethodName = switch (PaymentMethod.fromName(order.getPayment().getName())) {
                case PaymentMethod.CASH_BASED -> "Thanh toán tiền mặt (COD)";
                case PaymentMethod.BANK_TRANSFER -> "Chuyển khoản ngân hàng";
                case PaymentMethod.DIGITAL_WALLET -> "Ví điện tử";
                case PaymentMethod.CARD -> "Thẻ tín dụng / Ghi nợ";
                default -> throw new PaymentException(PaymentErrMsg.METHOD_NOT_FOUND);
            };
        }

        // 4. Payload hoàn chỉnh
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("orderId", order.getId());
        payloadMap.put("customer", customerMap);
        payloadMap.put("items", itemsList);
        payloadMap.put("totalAmount", order.getTotal());
        payloadMap.put("orderDate", formattedOrderDate);
        payloadMap.put("estimatedDelivery", formattedEstimatedDelivery);
        payloadMap.put("paymentMethod", paymentMethodName);

        try {
            return objectMapper.writeValueAsString(payloadMap);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi build JSON payload cho đơn #" + order.getId(), e);
        }
    }
}