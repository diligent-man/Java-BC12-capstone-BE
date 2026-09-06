package com.ndt.capstone.exception.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


@RestControllerAdvice
public class UserExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        UserException.class
    })
    public ResponseEntity<ApiErrorResponse> handleUserException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }
}
