package com.ndt.capstone.payload.response.exception;


import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@AllArgsConstructor
public class ApiErrorResponse {
    protected String code;
    protected String message;
}
