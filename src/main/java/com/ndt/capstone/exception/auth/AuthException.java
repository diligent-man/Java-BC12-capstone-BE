package com.ndt.capstone.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ndt.capstone.enums.exception.AuthError;


@Getter
@RequiredArgsConstructor
public class AuthException extends RuntimeException {
    private final AuthError error;
}
