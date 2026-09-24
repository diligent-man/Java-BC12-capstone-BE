package com.ndt.capstone.mapper;

import com.ndt.capstone.dto.CountryDTO;
import com.ndt.capstone.entity.CountryEntity;


public class CountryMapper {
    private CountryMapper() {
    }


    public static CountryDTO toDTO(CountryEntity obj) {
        if (obj == null) {
            return null;
        }

        CountryDTO dto = new CountryDTO();
        dto.setIso(obj.getIso());
        dto.setName(obj.getNiceName());
        dto.setPhoneCode(obj.getPhoneCode());
        return dto;

    }
}
