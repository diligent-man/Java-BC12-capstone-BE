package com.ndt.capstone.exception.order;

import com.ndt.capstone.exception.BaseException;
import com.ndt.capstone.enums.exception.OrderErrMsg;


public class OrderException extends BaseException {
    public OrderException(OrderErrMsg errorMsg) {
        super(errorMsg);
    }


    public OrderException(OrderErrMsg errorMsg, String overrideMsg) {
        super(errorMsg, overrideMsg);
    }
}
