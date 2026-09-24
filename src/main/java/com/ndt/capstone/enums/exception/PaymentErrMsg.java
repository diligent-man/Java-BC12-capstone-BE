package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum PaymentErrMsg implements ErrorMsg {
    METHOD_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment method not found"),
    METHOD_UNSUPPORTED(HttpStatus.BAD_REQUEST, "Payment method not supported"),

    STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment status not found"),
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
