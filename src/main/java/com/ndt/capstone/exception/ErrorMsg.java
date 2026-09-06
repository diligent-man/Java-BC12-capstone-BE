package com.ndt.capstone.exception;

import java.util.Arrays;


import org.springframework.http.HttpStatusCode;


public interface ErrorMsg {
    HttpStatusCode getHttpStatus();


    String getErrorMsg();


    static <T extends Enum<T> & ErrorMsg> T fromErrorResponse(Class<T> enumClass, HttpStatusCode code) {
        return Arrays.stream(enumClass.getEnumConstants())
            .filter(e -> e.getHttpStatus().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No match for: " + code));
    }
}
