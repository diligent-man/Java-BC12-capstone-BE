package com.ndt.capstone.mapper;

import org.jspecify.annotations.NonNull;


import com.ndt.capstone.dto.SizeDTO;
import com.ndt.capstone.entity.SizeEntity;


public class SizeMapper {
    private SizeMapper() {
    }


    public static SizeDTO toDTO(@NonNull SizeEntity obj) {
        SizeDTO dto = new SizeDTO();
        dto.setName(obj.getName());
        return dto;
    }
}
