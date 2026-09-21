package com.ndt.capstone.job;

import com.ndt.capstone.entity.OrderEntity;
import com.ndt.capstone.entity.PaymentStatusEntity;
import com.ndt.capstone.repository.OrderRepository;
import com.ndt.capstone.repository.PaymentStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTimeoutJob {

    private final OrderRepository orderRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    // fixedDelay = 15000: Cứ 15 giây bác bảo vệ lại đi tuần tra 1 lần
    @Scheduled(fixedDelay = 15000)
    @Transactional
    public void scanAndCancelExpiredOrders() {
        // 1. Lấy các đơn hàng đã tạo quá 60 giây do MySQL tự tính
        List<OrderEntity> expiredOrders = orderRepository.findExpiredOrders();
        // 2. Nếu có thì mới hủy
        if (!expiredOrders.isEmpty()) {
            for (OrderEntity order : expiredOrders) {
                PaymentStatusEntity canceledStatus = paymentStatusRepository.findById(4)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy payment status!"));
                order.setStatus(canceledStatus);
                orderRepository.save(order);
                log.info("Đơn hàng #{} đã quá 1 phút chưa chuyển tiền -> Tự động chuyển sang CANCELLED_PAYMENT", order.getId());
            }
        }
    }
}