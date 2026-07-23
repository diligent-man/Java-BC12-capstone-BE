package com.ndt.capstone.payload.response;


import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private String code;

    private String status;
}
