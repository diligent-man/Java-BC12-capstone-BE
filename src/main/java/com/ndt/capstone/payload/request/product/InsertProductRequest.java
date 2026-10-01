package com.ndt.capstone.payload.request.product;

import java.util.Set;
import java.math.BigDecimal;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;


import lombok.Data;


@Data
public class InsertProductRequest {
    @NotNull
    private String name;

    @Nullable
    private String information;

    @Nullable
    private String description;

    @Min(0)
    @NotNull
    @Digits(fraction = 2, integer = 9)
    private BigDecimal price;


    @NotBlank
    @Size(max = 50)
    private String brandName;

    @Size(min = 1, message = "Product must belong to at least 1 category")
    private Set<String> categoryNames;

    @Size(min = 1, message = "Product must belong to at least 1 tag")
    private Set<String> tagNames;
}
