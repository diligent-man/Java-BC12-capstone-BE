package com.ndt.capstone.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;


import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "outbox_event")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private PaymentStatusEntity status;

    @Column(nullable = false)
    private int retryCount = 0;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;
}
