package com.ndt.capstone.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "outbox_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long aggregateId;
    @Column(nullable = false, length = 100)
    private String eventType;
    @Column(nullable = false, length = 200)
    private String topic;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;
    @Column(nullable = false, length = 20)
    private String status;
    @Column(nullable = false)
    private int retryCount = 0;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

}