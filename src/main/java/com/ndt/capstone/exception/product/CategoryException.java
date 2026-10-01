package com.ndt.capstone.exception.product;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.CategoryErrMsg;


public class CategoryException extends BaseException {
    public CategoryException(CategoryErrMsg errorMsg) {
        super(errorMsg);
    }


    public CategoryException(CategoryErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
