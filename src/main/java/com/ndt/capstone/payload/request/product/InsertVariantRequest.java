package com.ndt.capstone.payload.request.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
public class InsertVariantRequest {
    private Long idProduct;

    private int idColor;

    private int idSize;

    private int quantity;

    private MultipartFile file;
}