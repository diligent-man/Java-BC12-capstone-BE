package com.ndt.capstone.payload.response.exception;

import java.time.Instant;

import java.sql.Timestamp;


import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class AuthErrorResponse extends ApiErrorResponse {
    private String path;

    @Builder.Default
    private Timestamp timestamp = Timestamp.from(Instant.now());
}
