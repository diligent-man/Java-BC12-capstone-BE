package com.ndt.capstone.enums.exception;

import com.ndt.capstone.exception.ErrorMsg;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum SizeErrMsg implements ErrorMsg {
    SIZE_NOT_FOUND(HttpStatus.NOT_FOUND, "Size not found"),
    ;

    private final HttpStatusCode httpStatusCode;

    @ToString.Include
    private final String errorMsg;
}
