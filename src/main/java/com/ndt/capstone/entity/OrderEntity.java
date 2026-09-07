package com.ndt.capstone.entity;

import java.sql.Timestamp;
import java.math.BigDecimal;

import jakarta.persistence.*;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 11, scale = 2)
    private BigDecimal total;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_payment")
    private PaymentMethodEntity payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @Column(nullable = false, insertable = false, updatable = false)
    private Timestamp createDate;
}
