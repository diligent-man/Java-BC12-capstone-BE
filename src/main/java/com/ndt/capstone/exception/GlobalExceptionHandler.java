package com.ndt.capstone.exception;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.multipart.MultipartException;


import com.ndt.capstone.enums.exception.GenericError;
import com.ndt.capstone.payload.response.ApiErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({GenericException.class})
    public ResponseEntity<ApiErrorResponse> handleGenericException(GenericException ex) {
        return buildApiErrorResponse(ex.getGenericError());
    }


    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiErrorResponse> handleMultipartException() {
        return buildApiErrorResponse(GenericError.MULTIPART_ERROR);
    }
}
