package com.ndt.capstone.enums.exception;

import com.ndt.capstone.exception.ErrorMsg;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum OrderErrMsg implements ErrorMsg {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found."),
    ORDER_NOT_PENDING(HttpStatus.BAD_REQUEST, "Order is not pending."),
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
