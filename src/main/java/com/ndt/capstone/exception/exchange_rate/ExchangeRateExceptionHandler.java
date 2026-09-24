package com.ndt.capstone.exception.exchange_rate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.exception.BaseExceptionHandler;
import com.ndt.capstone.payload.response.exception.ApiErrorResponse;


@RestControllerAdvice
public class ExchangeRateExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({
        ExchangeRateException.class
    })
    public ResponseEntity<ApiErrorResponse> handleExchangeRateException(BaseException ex) {
        return buildResponse(ex.getErrorMsg(), ex.getOverrideMsg());
    }
}
