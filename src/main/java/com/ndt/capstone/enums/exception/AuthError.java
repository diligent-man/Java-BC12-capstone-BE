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
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    ACCOUNT_TEMP_LOCKED(HttpStatus.FORBIDDEN, "Account temporarily locked for 15 minutes"),
    ACCOUNT_PERMANENTLY_LOCKED(HttpStatus.FORBIDDEN, "Account permanently locked, contact admin"),
    ACCOUNT_ACTIVE_SESSION(HttpStatus.FORBIDDEN, "Account is being used on another browser, please logout first"),
    SESSION_INVALID(HttpStatus.UNAUTHORIZED, "Session invalid, please login again"),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String message;
}
