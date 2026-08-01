package com.ndt.capstone.payload.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String fullname;

}
