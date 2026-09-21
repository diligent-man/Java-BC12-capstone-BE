package com.ndt.capstone.exception;

import org.springframework.http.HttpStatusCode;


public interface ErrorMsg {
    HttpStatusCode getHttpStatusCode();


    String getErrorMsg();
}
