package com.ndt.capstone.mapper;

import java.util.*;
import java.nio.file.Paths;


import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.entity.ProductEntity;
import com.ndt.capstone.entity.ProductVariantEntity;


public class ProductMapper {
    private ProductMapper() {
    }


    public static ProductDTO toDTO(ProductEntity obj, String defaultImage) {
        if (obj == null)
            return null;

        ProductDTO dto = new ProductDTO();

        dto.setName(obj.getName());
        dto.setPrice(obj.getPrice());

        Set<ProductVariantEntity> variants = obj.getVariants();

        // always retrieve the first variant of specific product
        if (!variants.isEmpty()) {
            dto.setImage(
                variants
                    .stream()
                    .min(Comparator.comparingLong(ProductVariantEntity::getSku))
                    .stream()
                    .findFirst()
                    .map(
                        variant -> {
                            String images = variant.getImages();

                            if (images == null || images.isBlank()) {
                                return defaultImage;
                            }

                            return Arrays
                                .stream(images.split(", "))
                                .sorted()
                                .toList()
                                .stream()
                                .findFirst()
                                .map(image -> buildVariantImagePath(
                                        variant.getProduct().getBrand().getName(),
                                        variant.getProduct().getName().replace(" ", "_"),
                                        image
                                    )
                                ).orElse(defaultImage);
                        }
                    ).orElse(defaultImage)
            );
        }
        return dto;
    }


    private static String buildVariantImagePath(String brand, String productName, String image) {
        return Paths.get(brand, productName, image).toString();
    }
}
