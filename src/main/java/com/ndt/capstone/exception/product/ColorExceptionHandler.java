package com.ndt.capstone.exception.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.resp.exception.ApiErrorResponse;


@RestControllerAdvice
public class ColorExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        ColorException.class
    })
    public ResponseEntity<ApiErrorResponse> handleColorException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }
}
