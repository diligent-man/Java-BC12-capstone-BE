package com.ndt.capstone.exception.auth;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.AuthErrMsg;


public final class AuthException extends BaseException {
    public AuthException(AuthErrMsg errorMsg) {
        super(errorMsg, null);
    }


    public AuthException(AuthErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
