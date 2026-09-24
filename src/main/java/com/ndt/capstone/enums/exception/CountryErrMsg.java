package com.ndt.capstone.enums.exception;

import com.ndt.capstone.exception.ErrorMsg;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum CountryErrMsg implements ErrorMsg {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Country not found"),
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
