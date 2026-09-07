package com.ndt.capstone.entity;

import jakarta.persistence.*;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "billing_details")
public class BillingDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", nullable = false)
    private CountryEntity country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false, unique = true)
    private OrderEntity order;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 50)
    private String town;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String zipCode;

    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(insertable = false, updatable = false)
    private Timestamp createDate;
}
