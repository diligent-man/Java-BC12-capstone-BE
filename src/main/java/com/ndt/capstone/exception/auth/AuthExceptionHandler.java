package com.ndt.capstone.exception.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.ExpiredJwtException;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.enums.exception.AuthError;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.ApiErrorResponse;


@RestControllerAdvice
public class AuthExceptionHandler implements BaseExceptionHandler {

    // THÊM METHOD NÀY: Bắt AuthException từ doLogin()
    @ExceptionHandler({
            AuthException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthException(AuthException ex) {
        return ResponseEntity
                .status(ex.getError().getHttpStatus())
                .body(new ApiErrorResponse(
                        String.valueOf(ex.getError().getHttpStatus().value()),
                        ex.getCustomMessage() //ex.getCustomMessage() → lấy đúng chuỗi đã được .replace()
                ));
    }

    @ExceptionHandler({
        ExpiredJwtException.class,
        JwtException.class,
        IllegalArgumentException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleJwtException() {

        return buildApiErrorResponse(AuthError.FAIL);
    }
}
