package com.ndt.capstone.job;

import java.util.List;
import java.time.LocalDateTime;


import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;


import com.ndt.capstone.entity.OutboxEventEntity;
import com.ndt.capstone.entity.PaymentStatusEntity;

import com.ndt.capstone.service.KafkaProducerService;

import com.ndt.capstone.repository.OutboxEventRepository;
import com.ndt.capstone.repository.PaymentStatusRepository;


@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisherJob {
    private final OutboxEventRepository outboxEventRepository;

    private final KafkaProducerService kafkaProducerService;

    private final PaymentStatusRepository paymentStatusRepository;


    // Cứ 15 giây "bác đưa thư" lại kiểm tra hòm thư outbox 1 lần
    @Scheduled(fixedDelay = 15000)
    public void publishPendingEvents() {
        // 1. Lấy tối đa 50 event CHƯA GỬI, sắp xếp cũ nhất trước
        List<OutboxEventEntity> pendingEvents = outboxEventRepository.findTop50ByStatus_IdOrderByCreatedAtAsc(1);

        // 2. Nếu không có event nào thì bỏ qua, không log gì cả
        if (pendingEvents.isEmpty()) {
            return;
        }

        log.info("[Outbox] Tìm thấy {} event đang chờ gửi", pendingEvents.size());

        // 3. Duyệt từng event, gửi lên Kafka
        for (OutboxEventEntity event : pendingEvents) {
            try {
                // 3a. Lấy topic và payload từ record outbox
                //     Ví dụ: topic = "order.payment", payload = "{\"orderId\":5,...}"
                kafkaProducerService.send(event.getTopic(), event.getPayload());

                // 3b. Gửi thành công → Đánh dấu PUBLISHED + ghi thời điểm gửi
                PaymentStatusEntity publishedStatus = paymentStatusRepository.findById(3)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy payment status!"));
                event.setStatus(publishedStatus);
                event.setPublishedAt(LocalDateTime.now());
                outboxEventRepository.save(event);

                log.info("[Outbox] Đã publish event #{} cho đơn hàng #{} lên topic '{}'",
                    event.getId(), event.getAggregateId(), event.getTopic());

            } catch (Exception e) {
                // 3c. Gửi thất bại → Tăng retryCount, log lỗi, KHÔNG đánh dấu PUBLISHED
                //     Lần chạy sau (15 giây nữa) sẽ thử gửi lại
                event.setRetryCount(event.getRetryCount() + 1);
                outboxEventRepository.save(event);

                log.error("[Outbox] Lỗi khi publish event #{}: {}",
                    event.getId(), e.getMessage());
            }
        }
    }
}
