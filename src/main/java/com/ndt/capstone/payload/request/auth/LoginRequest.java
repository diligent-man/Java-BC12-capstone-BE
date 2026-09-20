package com.ndt.capstone.payload.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


import lombok.Data;


@Data
public class LoginRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean rememberMe = false;
}
