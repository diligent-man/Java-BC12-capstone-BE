package com.ndt.capstone.payload.response.jwt;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class GenTokenResponse {
    private String token;
}
