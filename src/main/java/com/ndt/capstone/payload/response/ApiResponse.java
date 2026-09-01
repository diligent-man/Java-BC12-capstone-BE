package com.ndt.capstone.payload.response;

import lombok.Getter;
import lombok.Setter;

import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@SuperBuilder
public class ApiResponse {
    protected String code;

    protected String status;

    protected Object data;
}
