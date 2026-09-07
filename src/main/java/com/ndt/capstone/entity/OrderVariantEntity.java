package com.ndt.capstone.entity;

import java.math.BigDecimal;


import jakarta.persistence.*;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "order_variant")
@IdClass(OrderVariantId.class)
public class OrderVariantEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    private OrderEntity order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_variant", nullable = false)
    private ProductVariantEntity variant;

    private Integer quantity;

    @Column(precision = 11, scale = 2)
    private BigDecimal price;
}