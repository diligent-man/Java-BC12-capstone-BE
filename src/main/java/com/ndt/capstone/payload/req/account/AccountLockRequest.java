package com.ndt.capstone.payload.req.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


import lombok.Data;


@Data
public class AccountLockRequest {
    @NotNull
    @Email
    private String userEmail;

    @NotNull
    @Email
    private String adminEmail;
}
