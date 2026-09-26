package com.ndt.capstone.service;

import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;


import com.ndt.capstone.exception.order.OrderException;
import com.ndt.capstone.payload.request.payment.*;
import jakarta.transaction.Transactional;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


import com.ndt.capstone.entity.*;
import com.ndt.capstone.repository.*;
import com.ndt.capstone.enums.exception.*;

import com.ndt.capstone.mapper.payment.CheckoutMapper;
import com.ndt.capstone.mapper.OrderPaymentPayloadMapper;

import com.ndt.capstone.enums.payment.PaymentMethod;
import com.ndt.capstone.enums.payment.PaymentStatus;

import com.ndt.capstone.exception.user.UserException;
import com.ndt.capstone.exception.country.CountryException;
import com.ndt.capstone.exception.payment.PaymentException;
import com.ndt.capstone.exception.product.ProductException;

import com.ndt.capstone.payload.response.vietqr.VietQrResponse;

import com.ndt.capstone.service.contract.external.VietQrService;
import com.ndt.capstone.service.contract.external.ExchangeRateService;

import com.ndt.capstone.dto.checkout.CheckoutDTO;
import com.ndt.capstone.service.contract.OrderService;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BigDecimal VND_ROUNDING_UNIT = BigDecimal.valueOf(1000);

    private final VietQrService vietQrService;

    private final ExchangeRateService exchangeRateService;

    // 1. Nhóm lưu dữ liệu (ghi vào 3 bảng DB)
    private final OrderRepository orderRepo;

    private final OrderVariantRepository orderVariantRepo;

    private final BillingDetailsRepository billingDetailsRepo;


    // 2. Nhóm kiểm tra tính hợp lệ (tìm kiếm xem có tồn tại không)
    private final UserRepository userRepo;

    private final PaymentMethodRepository paymentMethodRepository;

    private final CountryRepository countryRepository;

    private final ProductVariantRepository productVariantRepo;

    private final PaymentStatusRepository paymentStatusRepo;

    private final OutboxEventRepository outboxEventRepo;

    private final OrderPaymentPayloadMapper orderPaymentPayloadMapper;


    @Override
    @Transactional
    public CheckoutDTO processCheckout(CheckoutRequest req, Long userId) {
        List<OrderItemRequest> items = mergeDuplicateItem(req.getItems());

        UserEntity user = userRepo
            .findById(userId)
            .orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));

        PaymentMethod reqPaymentMethod = PaymentMethod.fromAlterName(req.getPaymentMethodName());
        PaymentMethodEntity paymentMethod = paymentMethodRepository
            .findByName(reqPaymentMethod.getName())
            .orElseThrow(() -> new PaymentException(PaymentErrMsg.METHOD_NOT_FOUND));

        // currently support bank transfer
        if (!reqPaymentMethod.equals(PaymentMethod.BANK_TRANSFER))
            throw new PaymentException(PaymentErrMsg.METHOD_UNSUPPORTED);

        CountryEntity country = countryRepository
            .findByIso(req.getBilling().getCountryIso())
            .orElseThrow(() -> new CountryException(CountryErrMsg.NOT_FOUND));

        PaymentStatusEntity paymentStatus = paymentStatusRepo
            .findByName(PaymentStatus.PENDING.name())
            .orElseThrow(() -> new PaymentException(PaymentErrMsg.STATUS_NOT_FOUND));

        OrderEntity savedOrder = orderRepo.save(CheckoutMapper.toOrderEntity(req, user, paymentMethod, paymentStatus));
        for (OrderItemRequest item : items) {
            ProductVariantEntity variant = productVariantRepo
                .findBySku(item.getSku())
                .orElseThrow(() -> new ProductException(ProductErrMsg.PRODUCT_VARIANT_NOT_FOUND));

            if (productVariantRepo.decreaseQuantity(variant.getSku(), item.getQuantity()) == 0)
                throw new ProductException(ProductErrMsg.OUT_OF_STOCK);

            orderVariantRepo.save(CheckoutMapper.toOrderVariantEntity(savedOrder, item, variant));
        }

        billingDetailsRepo.save(CheckoutMapper.toBillingDetailsEntity(req, savedOrder, country, user));

        BigDecimal amount = req
            .getTotalAmount()
            .multiply(exchangeRateService.getExchangeRate("VND"))
            .setScale(0, RoundingMode.HALF_UP)
            .divide(VND_ROUNDING_UNIT, 0, RoundingMode.HALF_UP)
            .multiply(VND_ROUNDING_UNIT);

        String transferContent = "DH" + savedOrder.getId();
        VietQrResponse vietQrResponse = vietQrService.generateQrCode(amount.toPlainString(), transferContent);
        return CheckoutMapper.toCheckoutDTO(savedOrder.getId(), amount, vietQrResponse.getQrUrl(), transferContent);
    }


    @Override
    @Transactional
    public void confirmPayment(OrderConfirmRequest req) {
        Long orderId = req.getOrderId();

        // update status
        OrderEntity order = orderRepo
            .findById(orderId)
            .orElseThrow(() -> new OrderException(OrderErrMsg.ORDER_NOT_FOUND, "Order not found for #" + orderId));

        if (Objects.isNull(order.getStatus()) || !order.getStatus().getName().equals(PaymentStatus.PENDING.name()))
            throw new OrderException(OrderErrMsg.ORDER_NOT_PENDING);

        PaymentStatusEntity paidStatus = paymentStatusRepo
            .findByName(PaymentStatus.PAID.name())
            .orElseThrow(() -> new PaymentException(PaymentErrMsg.STATUS_NOT_FOUND));

        order.setStatus(paidStatus);
        orderRepo.save(order);

        // retrieve billing data
        BillingDetailsEntity billing = billingDetailsRepo
            .findByOrderId(orderId)
            .orElseThrow(() -> new OrderException(OrderErrMsg.ORDER_NOT_FOUND));

        List<OrderVariantEntity> orders = orderVariantRepo.findByOrder_Id(orderId);

        // Gọi mapper để build payload → Service không cần biết chi tiết JSON
        String payload = orderPaymentPayloadMapper.buildPayload(order, billing, orders);

        // set trạng thái outboxevent là Pending
        PaymentStatusEntity outboxPendingStatus = paymentStatusRepo
            .findByName(PaymentStatus.PENDING.name())
            .orElseThrow(() -> new PaymentException(PaymentErrMsg.STATUS_NOT_FOUND));

        // Insert outbox
        OutboxEventEntity event = OutboxEventEntity.builder()
            .aggregateId(order.getId())
            .eventType("ORDER_PAYMENT_SUCCESS")
            .topic("order.payment")
            .payload(payload)
            .status(outboxPendingStatus)
            .retryCount(0)
            .build();

        outboxEventRepo.save(event);
    }


    private List<OrderItemRequest> mergeDuplicateItem(List<OrderItemRequest> items) {
        Map<Long, OrderItemRequest> mergedItem = new LinkedHashMap<>();
        for (OrderItemRequest item : items) {
            mergedItem.merge(item.getSku(), item, (a, b) -> {
                a.setQuantity(a.getQuantity() + b.getQuantity());
                return a;
            });
        }
        return new ArrayList<>(mergedItem.values());
    }
}
