package com.ndt.capstone.exception;

import java.util.Objects;


import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Getter
public abstract class BaseException extends RuntimeException {
    @SuppressWarnings("java:S1948")
    protected final ErrorMsg errorMsg;

    protected final String overrideMsg;


    protected <T extends ErrorMsg> BaseException(T errorMsg) {
        this(errorMsg, null);
    }


    protected <T extends ErrorMsg> BaseException(T errorMsg, String overrideMsg) {
        this.errorMsg = errorMsg;
        this.overrideMsg = overrideMsg;
    }


    @Override
    public String getMessage() {
        return Objects.requireNonNullElse(
            Objects.requireNonNullElse(overrideMsg, errorMsg.getErrorMsg()),
            super.getMessage()
        );
    }


    public HttpStatusCode getHttpStatusCode() {
        return errorMsg.getHttpStatusCode();
    }
}
