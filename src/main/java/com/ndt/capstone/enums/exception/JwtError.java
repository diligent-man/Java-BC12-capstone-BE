package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.BaseError;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum JwtError implements BaseError {
    KEY_CREATION_SUCCESS(HttpStatus.NOT_FOUND, "Create secret key successfully"),
    KEY_CREATION_FAIL(HttpStatus.OK, "Fail to create secret key"),

    TOKEN_CREATION_SUCCESS(HttpStatus.OK, "Create token successfully"),
    TOKEN_CREATION_FAIL(HttpStatus.OK, "Fail to create token"),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String message;
}
