package com.ndt.capstone.exception.payment;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.PaymentErrMsg;


public class PaymentException extends BaseException {
    public PaymentException(PaymentErrMsg errorMsg) {
        super(errorMsg);
    }


    public PaymentException(PaymentErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
