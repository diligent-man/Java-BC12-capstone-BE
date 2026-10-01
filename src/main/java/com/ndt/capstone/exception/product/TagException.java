package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.TagErrMsg;


public class TagException extends BaseException {
    public TagException(TagErrMsg errorMsg) {
        super(errorMsg);
    }


    public TagException(TagErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
