package com.ndt.capstone.mapper;

import org.jspecify.annotations.NonNull;


import com.ndt.capstone.dto.ColorDTO;
import com.ndt.capstone.entity.ColorEntity;


public class ColorMapper {
    private ColorMapper() {
    }


    public static ColorDTO toDTO(@NonNull ColorEntity obj) {
        ColorDTO dto = new ColorDTO();
        dto.setName(obj.getName());
        return dto;
    }
}
