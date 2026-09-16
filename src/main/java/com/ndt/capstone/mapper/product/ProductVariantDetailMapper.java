package com.ndt.capstone.mapper.product;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


import com.ndt.capstone.dto.product.ProductVariantDetailDTO;
import com.ndt.capstone.projection.product.ProductVariantRow;


public class ProductVariantDetailMapper {
    private ProductVariantDetailMapper() {
    }


    public static List<ProductVariantDetailDTO> toDTO(
        List<ProductVariantRow> rows,
        String defaultImage,
        String imageSeparator
    ) {
        Map<Long, List<ProductVariantRow>> rowsBySku = rows
            .stream()
            .filter(r -> r.getSku() != null)
            .collect(Collectors.groupingBy(ProductVariantRow::getSku, LinkedHashMap::new, Collectors.toList()));

        return rowsBySku.values().stream()
            .map(group -> {
                ProductVariantRow first = group.getFirst();

                Set<String> categories = group.stream()
                    .map(ProductVariantRow::getCategoryName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

                Set<String> tags = group.stream()
                    .map(ProductVariantRow::getTagName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

                Set<String> images = first.getImages() != null ?
                    Arrays.stream(first.getImages().split(imageSeparator))
                        .map(image -> buildVariantImagePath(first.getBrandName(), first.getName(), image))
                        .collect(Collectors.toSet()) :
                    Set.of(defaultImage);

                return ProductVariantDetailDTO.builder()
                    .sku(first.getSku())
                    .color(first.getColorName())
                    .size(first.getSizeName())
                    .quantity(first.getQuantity())
                    .price(first.getVariantPrice())
                    .categories(categories)
                    .tags(tags)
                    .images(images)
                    .build();
            })
            .toList();
    }


    private static String buildVariantImagePath(String brand, String productName, String image) {
        productName = productName.replace(" ", "_");
        return Paths.get(brand, productName, image).toString();
    }
}
