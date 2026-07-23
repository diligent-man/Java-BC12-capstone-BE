package com.ndt.capstone.payload.response;

import lombok.Getter;
import lombok.Setter;

import lombok.experimental.SuperBuilder;


@Setter
@Getter
@SuperBuilder
public class ApiResponse {
    protected String code;

    protected String status;

    protected Object data;
}
