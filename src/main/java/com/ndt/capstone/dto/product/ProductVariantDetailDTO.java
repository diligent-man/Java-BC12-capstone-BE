package com.ndt.capstone.dto.product;

import java.util.Set;
import java.math.BigDecimal;


import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDetailDTO {
    private Long sku;
    private String color;
    private String size;
    private Integer quantity;
    private BigDecimal price;
    private Set<String> categories;
    private Set<String> tags;
    private Set<String> images;
}
