package com.ndt.capstone.mapper.payment;

import java.math.BigDecimal;


import com.ndt.capstone.entity.*;

import com.ndt.capstone.dto.checkout.CheckoutDTO;

import com.ndt.capstone.payload.request.payment.CheckoutRequest;
import com.ndt.capstone.payload.request.payment.OrderItemRequest;


public class CheckoutMapper {
    private CheckoutMapper() {

    }


    public static OrderEntity toOrderEntity(
        CheckoutRequest req,
        UserEntity user,
        PaymentMethodEntity paymentMethod,
        PaymentStatusEntity paymentStatus
    ) {
        OrderEntity order = new OrderEntity();

        order.setTotal(req.getTotalAmount());
        order.setNote(req.getNote());
        order.setStatus(paymentStatus);
        order.setPayment(paymentMethod);
        order.setUser(user);
        return order;
    }


    public static OrderVariantEntity toOrderVariantEntity(
        OrderEntity order,
        OrderItemRequest orderItem,
        ProductVariantEntity variant
    ) {
        OrderVariantEntity orderVariant = new OrderVariantEntity();
        orderVariant.setOrder(order);
        orderVariant.setVariant(variant);
        orderVariant.setQuantity(orderItem.getQuantity());
        orderVariant.setPrice(orderItem.getPrice());
        return orderVariant;
    }


    public static BillingDetailsEntity toBillingDetailsEntity(
        CheckoutRequest req,
        OrderEntity order,
        CountryEntity country,
        UserEntity user
    ) {
        BillingDetailsEntity billingDetails = new BillingDetailsEntity();

        billingDetails.setOrder(order);
        billingDetails.setFirstName(req.getBilling().getFirstName());
        billingDetails.setLastName(req.getBilling().getLastName());
        billingDetails.setCompanyName(req.getBilling().getCompanyName());
        billingDetails.setCountry(country);
        billingDetails.setAddress(req.getBilling().getAddress());
        billingDetails.setTown(req.getBilling().getTown());
        billingDetails.setState(req.getBilling().getState());
        billingDetails.setZipCode(req.getBilling().getZipCode());
        billingDetails.setPhone(req.getBilling().getPhone());
        billingDetails.setEmail(user.getEmail());
        return billingDetails;
    }


    public static CheckoutDTO toCheckoutDTO(
        Long orderId,
        BigDecimal amount,
        String qrUrl,
        String transferContent
    ) {
        CheckoutDTO obj = new CheckoutDTO();

        obj.setOrderId(orderId);
        obj.setAmount(amount);
        obj.setQrUrl(qrUrl);
        obj.setTransferContent(transferContent);
        return obj;
    }
}
