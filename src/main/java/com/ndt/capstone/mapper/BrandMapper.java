package com.ndt.capstone.mapper;

import java.util.*;


import com.ndt.capstone.dto.ProductDTO;
import com.ndt.capstone.entity.ProductEntity;
import com.ndt.capstone.entity.VariantEntity;


public class BrandMapper {
    private BrandMapper() {
    }


    public static ProductDTO toDTO(ProductEntity obj, String defaultImage) {
        if (obj == null)
            return null;

        ProductDTO dto = new ProductDTO();

        dto.setName(obj.getName());
        dto.setPrice(obj.getPrice());

        Set<VariantEntity> variants = obj.getVariants();

        // always retrieve the first variant of specific product
        if (!variants.isEmpty()) {
            dto.setImage(
                variants
                    .parallelStream()
                    .min(Comparator.comparingLong(VariantEntity::getSku))
                    .stream()
                    .findFirst()
                    .map(
                        variant -> {
                            String images = variant.getImages();

                            if (images == null || images.isBlank())
                                return defaultImage;

                            return Arrays
                                .stream(images.split(", "))
                                .sorted()
                                .toList()
                                .stream()
                                .findFirst()
                                .orElse(defaultImage);
                        }
                    ).orElse(defaultImage)
            );
        }
        return dto;
    }
}
