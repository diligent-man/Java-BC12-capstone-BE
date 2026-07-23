package com.ndt.capstone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import com.ndt.capstone.enums.exception.GenericError;


@Getter
@RequiredArgsConstructor
public final class GenericException extends RuntimeException {
    private final GenericError genericError;
}
