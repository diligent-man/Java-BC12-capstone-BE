package com.ndt.capstone.mapper;

import java.util.*;


import com.ndt.capstone.dto.BrandDTO;
import com.ndt.capstone.entity.BrandEntity;


public class BrandMapper {
    private BrandMapper() {
    }


    public static BrandDTO toDTO(BrandEntity obj) {
        if (obj == null)
            return null;

        BrandDTO dto = new BrandDTO();

        dto.setName(obj.getName());
        return dto;
    }
}
