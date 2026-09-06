package com.ndt.capstone.exception.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.ExpiredJwtException;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.AuthErrMsg;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


@RestControllerAdvice
public class AuthExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        AuthException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }


    @ExceptionHandler({
        ExpiredJwtException.class,
        JwtException.class,
        IllegalArgumentException.class,
    })
    public ResponseEntity<ApiErrorResponse> handleJwtException() {
        return buildResponse(AuthErrMsg.FAIL);
    }
}
