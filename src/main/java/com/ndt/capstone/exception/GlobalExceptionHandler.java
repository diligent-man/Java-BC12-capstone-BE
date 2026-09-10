package com.ndt.capstone.exception;

import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.multipart.MultipartException;


import com.ndt.capstone.enums.exception.GenericErrMsg;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        GenericException.class
    })
    public ResponseEntity<ApiErrorResponse> handleGenericException(GenericException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }


    @ExceptionHandler({
        MultipartException.class
    })
    public ResponseEntity<ApiErrorResponse> handleMultipartException() {
        return buildResponse(GenericErrMsg.MULTIPART_ERROR);
    }


    // Handle validations from jakarta.validation
    @ExceptionHandler({
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return ResponseEntity
            .status(GenericErrMsg.BAD_REQUEST.getHttpStatus())
            .body(createErrorMsgDTO(GenericErrMsg.BAD_REQUEST, message, ApiErrorResponse::new));
    }
}
