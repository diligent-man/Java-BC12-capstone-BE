package com.ndt.capstone.job;

import java.util.List;


import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.entity.*;
import com.ndt.capstone.repository.*;

import com.ndt.capstone.enums.payment.PaymentStatus;
import com.ndt.capstone.enums.exception.PaymentErrMsg;

import com.ndt.capstone.exception.payment.PaymentException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTimeoutJob {
    @Value("${payment.pending-timeout:120}")
    private Integer pendingTimeout;

    private final OrderRepository orderRepo;

    private final OrderVariantRepository orderVariantRepo;

    private final PaymentStatusRepository paymentStatusRepo;

    private final ProductVariantRepository productVariantRepo;


    @Transactional
    @Scheduled(cron = "*/${payment.pending-timeout:120} * * * * *")
    public void scanAndCancelExpiredOrders() {
        List<OrderEntity> expiredOrders = orderRepo.findPendingOlderThan(pendingTimeout);
        if (!expiredOrders.isEmpty()) {
            for (OrderEntity order : expiredOrders) {
                PaymentStatusEntity canceledStatus = paymentStatusRepo
                    .findByName(PaymentStatus.CANCELED.name())
                    .orElseThrow(() -> new PaymentException(PaymentErrMsg.STATUS_NOT_FOUND));

                /*
                    The job loads an order as PENDING. Meanwhile confirmPayment marks it PAID and commits.
                    The job then sets it to CANCELED and restocks, so a paid order ends up canceled
                 */
                if (orderRepo.cancelIfPending(order.getId(), canceledStatus) == 1) {
                    for (OrderVariantEntity ov : orderVariantRepo.findByOrder_Id(order.getId()))
                        productVariantRepo.increaseQuantity(ov.getVariant().getSku(), ov.getQuantity());

                    order.setStatus(canceledStatus);

                    log.info(
                        "Đơn hàng #{} đã quá {} giây chưa chuyển tiền -> Tự động chuyển sang {}",
                        order.getId(),
                        pendingTimeout,
                        PaymentStatus.CANCELED.name()
                    );
                }
            }
        }
    }
}
