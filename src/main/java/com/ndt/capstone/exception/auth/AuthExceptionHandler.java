package com.ndt.capstone.exception.auth;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;


import io.jsonwebtoken.JwtException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.exception.AuthErrorResponse;


@RestControllerAdvice
public class AuthExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(BaseException ex) {
        return buildResponse(ex.getHttpStatusCode(), ex.getMessage());
    }


    @ExceptionHandler({
        JwtException.class,
        IllegalArgumentException.class,
        AuthenticationException.class
    })
    public ResponseEntity<AuthErrorResponse> handleAuthenticationException(HttpServletRequest req, Exception ex) {
        HttpStatus code = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(code).body(
            AuthErrorResponse.builder()
                .code(String.valueOf(code.value()))
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build()
        );
    }
}
