package com.ndt.capstone.payload.response;


public record ApiErrorResponse(String code, String status) implements ApiErrResp {

}