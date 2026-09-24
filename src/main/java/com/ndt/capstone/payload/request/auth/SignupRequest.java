package com.ndt.capstone.payload.request.auth;

import jakarta.validation.constraints.*;


import lombok.*;


@Data
public class SignupRequest {
    @Email
    @NotBlank
    @Size(min = 1, max = 50)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;
}
