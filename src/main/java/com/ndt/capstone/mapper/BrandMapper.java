package com.ndt.capstone.mapper;

import org.jspecify.annotations.NonNull;


import com.ndt.capstone.dto.BrandDTO;
import com.ndt.capstone.entity.BrandEntity;


public class BrandMapper {
    private BrandMapper() {
    }


    public static BrandDTO toDTO(@NonNull BrandEntity obj) {
        BrandDTO dto = new BrandDTO();
        dto.setName(obj.getName());
        return dto;
    }
}
