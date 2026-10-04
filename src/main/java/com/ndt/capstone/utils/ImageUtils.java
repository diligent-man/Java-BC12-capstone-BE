package com.ndt.capstone.utils;

import java.nio.file.Paths;


import com.ndt.capstone.enums.file.UploadImageType;


public final class ImageUtils {
    private ImageUtils() {
    }


    private static String preprocessProductName(String productName) {
        productName = productName.replace(" ", "_");
        return productName;
    }


    public static String buildVariantImageReadPath(String brand, String productName, String image) {
        return Paths.get(
            brand,
            preprocessProductName(productName),
            image
        ).toString();
    }


    public static String buildVariantImageSavePath(String root, String brand, String productName) {
        return Paths.get(
            root,
            UploadImageType.PRODUCT.getFolder(),
            brand,
            preprocessProductName(productName)
        ).toString();
    }
}
