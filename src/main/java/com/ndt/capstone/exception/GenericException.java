package com.ndt.capstone.exception;

import com.ndt.capstone.enums.exception.GenericErrMsg;


public final class GenericException extends BaseException {
    public GenericException(GenericErrMsg errorMsg) {
        super(errorMsg, null);
    }


    public GenericException(GenericErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
