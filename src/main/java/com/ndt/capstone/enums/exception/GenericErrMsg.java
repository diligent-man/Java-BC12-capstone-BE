package com.ndt.capstone.enums.exception;

import lombok.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


import com.ndt.capstone.exception.ErrorMsg;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum GenericErrMsg implements ErrorMsg {
    MULTIPART_ERROR(HttpStatus.OK, "Multipart error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatusCode httpStatus;

    @ToString.Include
    private final String errorMsg;
}
