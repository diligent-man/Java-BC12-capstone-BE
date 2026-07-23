package com.ndt.capstone.exception;

import java.util.Arrays;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


public interface BaseError {
    HttpStatusCode getHttpStatus();


    String getMessage();


    static <T extends Enum<T> & BaseError> T getFromHttpStatus(Class<T> enumClass, HttpStatusCode code) {
        return Arrays.stream(enumClass.getEnumConstants())
            .filter(e -> e.getHttpStatus().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No match for: " + code));
    }
}
