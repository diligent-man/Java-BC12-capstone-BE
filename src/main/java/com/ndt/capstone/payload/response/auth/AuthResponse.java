package com.ndt.capstone.payload.response.auth;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class AuthResponse {
    private final String accessToken;
}
