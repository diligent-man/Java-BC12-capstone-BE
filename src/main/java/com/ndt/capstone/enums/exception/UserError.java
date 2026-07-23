package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.BaseError;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum UserError implements BaseError {
    NOT_FOUND(HttpStatus.OK, "User not found"),

    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String message;
}
