package com.ndt.capstone.mapper.product;

import java.util.List;


import com.ndt.capstone.dto.product.ProductDetailDTO;
import com.ndt.capstone.dto.product.ProductVariantDetailDTO;
import com.ndt.capstone.projection.product.ProductVariantRow;


public class ProductDetailMapper {
    private ProductDetailMapper(){

    }

    public static ProductDetailDTO toDTO(ProductVariantRow row, List<ProductVariantDetailDTO> variants) {
        return ProductDetailDTO.builder()
            .name(row.getName())
            .price(row.getBasePrice())
            .information(row.getInformation())
            .description(row.getDescription())
            .variants(variants)
            .build();
    }
}
