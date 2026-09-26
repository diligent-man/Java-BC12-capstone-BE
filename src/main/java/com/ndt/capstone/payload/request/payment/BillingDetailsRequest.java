package com.ndt.capstone.payload.request.payment;

import jakarta.validation.constraints.*;


import lombok.Data;


@Data
public class BillingDetailsRequest {
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    private String companyName;

    @NotNull
    @Size(max = 2)
    private String countryIso;

    @NotBlank
    private String address;

    @NotBlank
    @Size(max = 50)
    private String town;

    @NotBlank
    @Size(max = 50)
    private String state;

    @NotBlank
    @Size(max = 50)
    private String zipCode;

    @NotBlank
    @Size(max = 12)
    private String phone;

}
