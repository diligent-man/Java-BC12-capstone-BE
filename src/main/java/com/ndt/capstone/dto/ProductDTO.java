package com.ndt.capstone.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductDTO {
    private int id;
    private String name;
    private BigDecimal price;
    private String[] image;
}
