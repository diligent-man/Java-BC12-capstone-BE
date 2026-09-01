package com.ndt.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;


@Getter
@Setter
@Entity(name = "variant")
public class VariantProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sku;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private ColorEntity Color;


    @ManyToOne
    @JoinColumn(name = "id_size")
    private SizeEntity idSize;

    private int quantity;

    private String images;
}
