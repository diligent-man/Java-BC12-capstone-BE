package com.ndt.capstone.payload.req.product;

import java.util.List;
import java.math.BigDecimal;


import jakarta.validation.constraints.*;


import lombok.*;


import org.springframework.web.multipart.MultipartFile;


import com.ndt.capstone.annotation.ValidFile;


@Data
public class InsertVariantRequest {
    @NotNull
    private Long idProduct;

    @NotBlank
    @Size(max = 20)
    private String colorName;

    @NotBlank
    @Size(max = 20)
    private String sizeName;

    @NotNull
    @Size(max = 50)
    private String brandName;

    @Positive
    @NotNull
    private int quantity;

    @Min(0)
    @NotNull
    @Digits(fraction = 2, integer = 9)
    private BigDecimal price;

    @Size(max = 10, message = "Maximum 10 images allowed")
    private List<@ValidFile(allowedTypes = {"image/jpeg", "image/png"}) MultipartFile> files;
}
