package com.ndt.capstone.exception.exchange_rate;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.ExchangeRateErrMsg;


public class ExchangeRateException extends BaseException {
    public ExchangeRateException(ExchangeRateErrMsg errorMsg) {
        super(errorMsg);
    }


    public ExchangeRateException(ExchangeRateErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
