package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.ColorErrMsg;


public class ColorException extends BaseException {
    public ColorException(ColorErrMsg errorMsg) {
        super(errorMsg);
    }


    public ColorException(ColorErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
