package com.ndt.capstone.dto.order;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long sku;
    private String productName;
    private String color;
    private String size;
    private String image;
    private Integer quantity;
    private BigDecimal price;
}