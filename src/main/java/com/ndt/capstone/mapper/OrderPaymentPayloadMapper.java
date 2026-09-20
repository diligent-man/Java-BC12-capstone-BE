package com.ndt.capstone.mapper;

import com.ndt.capstone.entity.*;

import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component

public class OrderPaymentPayloadMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public String buildPayload(OrderEntity order, BillingDetailsEntity billing,
                               List<OrderVariantEntity> orderItems){

        // 1. Thông tin khách hàng
        //xử lý SĐT khách hàng 4 3 3
        String phone = billing.getPhone();
        // Tách số điện thoại 10 số thành định dạng 4 - 3 - 3 (Ví dụ: 0901 234 567)
        if (phone != null && phone.trim().length() == 10) {
            String clean = phone.trim();
            phone = clean.substring(0, 4) + " " + clean.substring(4, 7) + " " + clean.substring(7);
        }
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
        LocalDateTime orderDate = order.getCreateDate().toLocalDateTime();
        LocalDateTime estimatedDelivery = orderDate.plusDays(2);
        // Format ngày đặt: yyyy-MM-dd HH:mm:ss (bỏ chữ T)
        String formattedOrderDate = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Format ngày giao dự kiến: chỉ lấy ngày yyyy-MM-dd (bỏ giờ)
        String formattedEstimatedDelivery = estimatedDelivery.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));



        //lấy ra ten của phương thức thanh toán
        String paymentMethodName = "Không xác định";
        if (order.getPayment() != null) {
            paymentMethodName = switch (order.getPayment().getId()) {
                case 1 -> "Thanh toán tiền mặt (COD)";
                case 2 -> "Chuyển khoản ngân hàng";
                case 3 -> "Ví điện tử";
                case 4 -> "Thẻ tín dụng / Ghi nợ";
                default -> order.getPayment().getName();
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