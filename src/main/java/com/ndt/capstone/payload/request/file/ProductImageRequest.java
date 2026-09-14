package com.ndt.capstone.payload.request.file;

import lombok.Data;


@Data
public class ProductImageRequest {
    private String brand;

    private String productName;

    private String filename;
}
