package com.ndt.capstone.payload.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String fullName;
}
