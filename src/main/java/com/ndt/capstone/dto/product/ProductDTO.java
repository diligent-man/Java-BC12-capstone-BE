package com.ndt.capstone.dto.product;

import java.math.BigDecimal;


import lombok.Data;


@Data
public class ProductDTO {
    private String name;

    private BigDecimal price;

    private String image;
}
