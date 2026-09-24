package com.ndt.capstone.exception.country;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.CountryErrMsg;


public class CountryException extends BaseException {
    public CountryException(CountryErrMsg errorMsg) {
        super(errorMsg);
    }


    public CountryException(CountryErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
