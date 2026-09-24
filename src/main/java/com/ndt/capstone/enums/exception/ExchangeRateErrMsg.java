package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum ExchangeRateErrMsg implements ErrorMsg {
    API_FETCH_FAIL(HttpStatus.NOT_FOUND, "Failed to fetch exchange rate."),
    API_FETCH_SUCESS(HttpStatus.OK, "Refreshed exchange rates into Redis. Fetched at"),

    NO_RESULT(HttpStatus.NO_CONTENT, "API called successfully but get null result")
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
