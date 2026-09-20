package com.ndt.capstone.job;

import com.ndt.capstone.entity.OutboxEventEntity;
import com.ndt.capstone.repository.OutboxEventRepository;
import com.ndt.capstone.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisherJob {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducerService kafkaProducerService;

    // Cứ 15 giây "bác đưa thư" lại kiểm tra hòm thư outbox 1 lần
    @Scheduled(fixedDelay = 15000)
    public void publishPendingEvents() {

        // 1. Lấy tối đa 50 event CHƯA GỬI, sắp xếp cũ nhất trước
        List<OutboxEventEntity> pendingEvents =
                outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc("PENDING");

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
                event.setStatus("PUBLISHED");
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