package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.ProductErrMsg;


public class ProductException extends BaseException {
    public ProductException(ProductErrMsg errorMsg) {
        super(errorMsg, null);
    }


    public ProductException(ProductErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
