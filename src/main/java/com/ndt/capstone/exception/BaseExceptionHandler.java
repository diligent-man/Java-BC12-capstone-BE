package com.ndt.capstone.exception;

import java.util.function.BiFunction;


import org.springframework.http.ResponseEntity;


import com.ndt.capstone.payload.response.exception.ApiErrResp;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


public interface BaseExceptionHandler {
    default <T extends ApiErrResp> T createErrorMsgDTO(
        ErrorMsg errorMsg,
        BiFunction<String, String, T> factory
    ) {
        return factory.apply(
            String.valueOf(errorMsg.getHttpStatus().value()),
            errorMsg.getErrorMsg()
        );
    }


    default <T extends ApiErrResp> T createErrorMsgDTO(
        ErrorMsg errorMsg,
        String overrideMsg,
        BiFunction<String, String, T> factory
    ) {
        return factory.apply(
            String.valueOf(errorMsg.getHttpStatus().value()),
            overrideMsg != null ? overrideMsg : errorMsg.getErrorMsg()
        );
    }


    default <T extends ErrorMsg> ResponseEntity<ApiErrorResponse> buildResponse(T errorMsg) {
        return ResponseEntity
            .status(errorMsg.getHttpStatus())
            .body(createErrorMsgDTO(errorMsg, null, ApiErrorResponse::new));
    }


    default <T extends ErrorMsg> ResponseEntity<ApiErrorResponse> buildResponse(T errorMsg, String overrideMsg) {
        return ResponseEntity
            .status(errorMsg.getHttpStatus())
            .body(createErrorMsgDTO(errorMsg, overrideMsg, ApiErrorResponse::new));
    }
}
