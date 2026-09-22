package com.ndt.capstone.payload.request.payment;

import com.ndt.capstone.entity.CountryEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class BillingDetailsRequest {
    @NotBlank(message = "firstName không được để trống")
    private String firstName;
    @NotBlank(message = "lastName không được để trống")
    private String lastName;
    private String companyName;
    @NotNull(message = "countryId không được để trống")
    private int countryId;
    @NotBlank(message = "address không được để trống")
    private String address;
    @NotBlank(message = "town không được để trống")
    private String town;
    @NotBlank(message = "state không được để trống")
    private String state;
    @NotBlank(message = "zipCode không được để trống")
    private String zipCode;
    @NotBlank(message = "phone không được để trống")
    private String phone;
    @NotBlank(message = "email không được để trống")
    private String email;
}