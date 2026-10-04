package com.ndt.capstone.payload.resp.jwt;

import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class GenKeyResponse {
    private String secreteKey;
}
