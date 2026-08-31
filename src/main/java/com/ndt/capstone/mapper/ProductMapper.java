package com.ndt.capstone.mapper;

import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.entity.ProductEntity;

public class ProductMapper {
    public static ProductDTO toProductDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        if(!product.getVariantProducts().isEmpty()){
            productDTO.setImage(
                    product.getVariantProducts().stream()
                            .findFirst()
                            .map(item -> item.getImages() != null ? item.getImages().split(",") : new String[]{"default.png"})
                            .orElse(new String[]{"default.png"})
            );
        }
        return productDTO;
    }
}
