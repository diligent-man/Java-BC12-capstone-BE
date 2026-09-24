package com.ndt.capstone.entity;

import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "payment_status")
public class PaymentStatusEntity {
    @Id
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
