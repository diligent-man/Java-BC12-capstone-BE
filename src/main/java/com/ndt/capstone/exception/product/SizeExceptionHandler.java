package com.ndt.capstone.exception.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.resp.exception.ApiErrorResponse;


@RestControllerAdvice
public class SizeExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        SizeException.class
    })
    public ResponseEntity<ApiErrorResponse> handleSizeException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }
}
