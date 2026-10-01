package com.ndt.capstone.utils;

import java.nio.file.Paths;


// TODO: refactor later
public class ImageUtils {
    public static String buildVariantImagePath(String brand, String productName, String image) {
        productName = productName.replace(" ", "_");
        return Paths.get(brand, productName, image).toString();
    }
}
