package com.ndt.capstone.exception;

import java.util.function.BiFunction;


import org.springframework.http.*;


import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


public interface BaseExceptionHandler {
    default <T extends ApiErrorResponse> T createApiErrorResponse(
        HttpStatusCode code,
        String message,
        BiFunction<String, String, T> factory
    ) {
        return factory.apply(String.valueOf(code.value()), message);
    }


    default <T extends ApiErrorResponse> T createApiErrorResponse(
        ErrorMsg errorMsg,
        String overrideMsg,
        BiFunction<String, String, T> factory
    ) {
        return factory.apply(
            String.valueOf(errorMsg.getHttpStatusCode().value()),
            overrideMsg != null ? overrideMsg : errorMsg.getErrorMsg()
        );
    }


    default <T extends ErrorMsg> ResponseEntity<ApiErrorResponse> buildResponse(T errorMsg) {
        return ResponseEntity
            .status(errorMsg.getHttpStatusCode().value())
            .body(createApiErrorResponse(errorMsg, null, ApiErrorResponse::new));
    }


    default <T extends ErrorMsg> ResponseEntity<ApiErrorResponse> buildResponse(T errorMsg, String overrideMessage) {
        return ResponseEntity
            .status(errorMsg.getHttpStatusCode().value())
            .body(createApiErrorResponse(errorMsg, overrideMessage, ApiErrorResponse::new));
    }


    default ResponseEntity<ApiErrorResponse> buildResponse(HttpStatusCode code, String message) {
        return ResponseEntity
            .status(code.value())
            .body(createApiErrorResponse(code, message, ApiErrorResponse::new));
    }
}
