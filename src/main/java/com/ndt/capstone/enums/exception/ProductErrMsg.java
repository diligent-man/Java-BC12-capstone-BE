package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum ProductErrMsg implements ErrorMsg {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    PRODUCT_EXISTED_BY_BRAND(HttpStatus.CONFLICT, "Brand had this product name"),
    PRODUCT_VARIANT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product variant not found"),

    VARIANT_EXISTED(HttpStatus.CONFLICT, "Variant existed"),
    VARIANT_OUT_OF_STOCK(HttpStatus.UNPROCESSABLE_CONTENT, "Out of stock"),
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
