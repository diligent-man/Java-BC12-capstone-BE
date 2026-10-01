package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.SizeErrMsg;


public class SizeException extends BaseException {
    public SizeException(SizeErrMsg errorMsg) {
        super(errorMsg);
    }


    public SizeException(SizeErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
