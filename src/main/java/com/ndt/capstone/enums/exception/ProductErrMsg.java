package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum ProductErrMsg implements ErrorMsg {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String errorMsg;
}
