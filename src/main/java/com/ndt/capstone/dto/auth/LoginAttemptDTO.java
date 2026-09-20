package com.ndt.capstone.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@Builder
public class LoginAttemptDTO {
    @NonNull
    private Long remainingAttempts;

    @NotNull
    private Boolean locked;
}
