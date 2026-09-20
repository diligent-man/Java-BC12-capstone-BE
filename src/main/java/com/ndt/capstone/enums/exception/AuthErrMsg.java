package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum AuthErrMsg implements ErrorMsg {
    SIGNIN_SUCCESS(HttpStatus.OK, "Signed in successfully"),
    SIGNIN_FAIL(HttpStatus.OK, "Signed in failed"),

    SIGNOUT_SUCCESS(HttpStatus.OK, "Signed out successfully"),

    SIGNUP_SUCCESS(HttpStatus.OK, "Signed up successfully. Please, check your mail box."),

    // Login security errors
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    ACCOUNT_TEMP_LOCKED(HttpStatus.FORBIDDEN, "Account temporarily locked, retry after"),
    ACCOUNT_PERMANENTLY_LOCKED(HttpStatus.FORBIDDEN, "Account permanently locked, contact admin"),
    ACCOUNT_ACTIVE_SESSION(HttpStatus.FORBIDDEN, "Account is being used on another browser, please logout first"),
    SESSION_INVALID(HttpStatus.UNAUTHORIZED, "Session invalid, please login again"),
    ;

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String errorMsg;
}
