package com.ndt.capstone.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import jakarta.persistence.*;


import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "variant")
public class VariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sku;

    // stored as comma-separated or JSON string
    @Column(columnDefinition = "TEXT")
    private String images;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private ColorEntity color;


    @ManyToOne
    @JoinColumn(name = "id_size")
    private SizeEntity idSize;
}
