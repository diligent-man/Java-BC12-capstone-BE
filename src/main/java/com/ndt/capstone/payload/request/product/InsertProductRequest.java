package com.ndt.capstone.payload.request.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class InsertProductRequest {
    private String name;

    private String information;

    private String description;

    private BigDecimal price;

    private int idBrand;
}
