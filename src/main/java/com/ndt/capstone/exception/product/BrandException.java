package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.BrandErrMsg;


public class BrandException extends BaseException {
    public BrandException(BrandErrMsg errorMsg) {
        super(errorMsg);
    }


    public BrandException(BrandErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
