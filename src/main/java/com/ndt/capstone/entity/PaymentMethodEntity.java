package com.ndt.capstone.entity;

import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "payment_method")
public class PaymentMethodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
