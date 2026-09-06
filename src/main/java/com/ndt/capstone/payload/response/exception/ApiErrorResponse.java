package com.ndt.capstone.payload.response.exception;


public record ApiErrorResponse(String code, String status) implements ApiErrResp {

}