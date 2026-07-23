package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.BaseError;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum AuthError implements BaseError {
    SUCCESS(HttpStatus.OK, "Authentication successful"),
    FAIL(HttpStatus.OK, "Authentication failed"),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String message;
}
