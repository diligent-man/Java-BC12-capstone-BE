package com.ndt.capstone.mapper.product;

import java.util.*;


import org.jspecify.annotations.NonNull;


import com.ndt.capstone.entity.*;

import com.ndt.capstone.dto.product.ProductDTO;
import com.ndt.capstone.payload.request.product.InsertProductRequest;

import static com.ndt.capstone.utils.ImageUtils.buildVariantImagePath;


public class ProductMapper {
    private ProductMapper() {
    }


    public static ProductDTO toDTO(
        @NonNull ProductEntity obj,
        String defaultImage
    ) {

        ProductDTO dto = new ProductDTO();

        dto.setId(obj.getId());
        dto.setName(obj.getName());
        dto.setPrice(obj.getPrice());

        if (Objects.nonNull(obj.getBrand())) {
            dto.setBrandName(obj.getBrand().getName());
        }

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
                                        variant.getProduct().getName(),
                                        image
                                    )
                                ).orElse(defaultImage);
                        }
                    ).orElse(defaultImage)
            );
        }
        return dto;
    }


    public static ProductEntity toEntity(InsertProductRequest obj, BrandEntity brand) {
        ProductEntity entity = new ProductEntity();
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setInformation(obj.getInformation());
        entity.setPrice(obj.getPrice());
        entity.setBrand(brand);
        return entity;
    }
}
