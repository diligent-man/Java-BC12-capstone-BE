package com.ndt.capstone.exception.country;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


@RestControllerAdvice
public class CountryExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        CountryException.class
    })
    public ResponseEntity<ApiErrorResponse> handleProductException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }
}
