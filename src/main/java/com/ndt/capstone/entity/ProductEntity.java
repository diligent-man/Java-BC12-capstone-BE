package com.ndt.capstone.entity;

import java.util.Set;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import jakarta.persistence.*;


import lombok.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @Column(columnDefinition = "TEXT")
    private String description;

    @ToString.Exclude
    @Column(columnDefinition = "TEXT")
    private String information;

    @Column(precision = 11, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private BrandEntity brand;


    @Column(name = "create_date")
    private LocalDateTime createDate;


    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private Set<ProductVariantEntity> variants;
}
