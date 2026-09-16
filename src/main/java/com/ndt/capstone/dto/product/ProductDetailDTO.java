package com.ndt.capstone.dto.product;

import java.util.*;
import java.math.BigDecimal;


import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    // TODO: add rating data
    private String name;
    // private Double rating;
    private BigDecimal price;
    private String brand;
    private String information;
    private String description;
    private List<ProductVariantDetailDTO> variants;
}
