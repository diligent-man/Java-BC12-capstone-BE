package com.ndt.capstone.exception;

import java.util.function.BiFunction;


import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.ApiErrorResponse;


public interface BaseExceptionHandler {
    default <T extends ApiErrorResponse> T createErrorMessageDto(
        BaseError errorMsg,
        BiFunction<String, String, T> factory
    ) {
        return factory.apply(
            String.valueOf(errorMsg.getHttpStatus().value()),
            errorMsg.getMessage()
        );
    }


    default ResponseEntity<ApiErrorResponse> buildApiErrorResponse(
        BaseError errorMsg
    ) {
        return ResponseEntity
            .status(errorMsg.getHttpStatus())
            .body(createErrorMessageDto(errorMsg, ApiErrorResponse::new));
    }
}
