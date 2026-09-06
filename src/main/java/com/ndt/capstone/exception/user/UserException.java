package com.ndt.capstone.exception.user;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.UserErrMsg;


public class UserException extends BaseException {
    public UserException(UserErrMsg errorMsg) {
        super(errorMsg, null);
    }


    public UserException(UserErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
