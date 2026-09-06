package com.ndt.capstone.payload.response;

import lombok.*;

import lombok.experimental.SuperBuilder;


import org.springframework.http.HttpStatus;


@Setter
@Getter
@SuperBuilder
public class ApiResponse {
    @Builder.Default
    protected String code = String.valueOf(HttpStatus.OK);

    @Builder.Default
    protected String message = "success";

    protected Object data;
}
