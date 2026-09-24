package com.ndt.capstone.dto;

import lombok.Data;


@Data
public class CountryDTO {
    private String iso;

    private String name;

    private Integer phoneCode;
}
