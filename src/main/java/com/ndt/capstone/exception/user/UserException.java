package com.ndt.capstone.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import com.ndt.capstone.enums.exception.UserError;


@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserError error;
}
