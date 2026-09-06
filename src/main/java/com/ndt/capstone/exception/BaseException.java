package com.ndt.capstone.exception;

import lombok.Getter;


@Getter
public abstract class BaseException extends RuntimeException {
    protected final ErrorMsg errorMsg;

    protected final String overrideMsg;


    protected  <T extends ErrorMsg> BaseException(T errorMsg) {
        this(errorMsg, null);
    }


    protected  <T extends ErrorMsg> BaseException(T errorMsg, String overrideMsg) {
        this.errorMsg = errorMsg;
        this.overrideMsg = overrideMsg;
    }
}
