package com.ndt.capstone.projection.product;

import java.math.BigDecimal;


public interface ProductVariantRow {
    String getName();
    String getDescription();
    String getInformation();
    BigDecimal getBasePrice();
    String getBrandName();
    Long getSku();
    Integer getQuantity();
    BigDecimal getVariantPrice();
    String getCategoryName();
    String getTagName();
    String getImages();
    String getColorName();
    String getSizeName();
}
