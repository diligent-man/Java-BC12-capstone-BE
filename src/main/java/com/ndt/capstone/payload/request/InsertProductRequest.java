package com.ndt.capstone.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Setter
@Getter
public class InsertProductRequest {
    private MultipartFile file;
    private String name;
    private String information;
    private String description;
    private BigDecimal price;
    private int idBrand;
    private int idSize;
    private int idColor;
    private int quantity;
}
