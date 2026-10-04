package com.ndt.capstone.exception;

import java.time.Instant;
import java.sql.SQLException;
import java.util.stream.Collectors;


import org.apache.kafka.common.errors.ResourceNotFoundException;


import org.springframework.http.*;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.multipart.MultipartException;


import com.ndt.capstone.enums.exception.GenericErrMsg;
import com.ndt.capstone.payload.resp.exception.ApiErrorResponse;


// should extend ResponseEntityExceptionHandler class ?
@RestControllerAdvice
public class GlobalExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        GenericException.class
    })
    public ResponseEntity<ApiErrorResponse> handleGenericException(GenericException ex) {
        return buildResponse(ex.getHttpStatusCode(), "An unexpected error occurred");
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Resource Not Found");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorResponse> handleSqlException(SQLException ex) {
        return buildResponse(GenericErrMsg.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiErrorResponse> handleMultipartException() {
        return buildResponse(GenericErrMsg.MULTIPART_ERROR);
    }


    // Handle validations from jakarta.validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return buildResponse(GenericErrMsg.BAD_REQUEST.getHttpStatusCode(), message);
    }
}
