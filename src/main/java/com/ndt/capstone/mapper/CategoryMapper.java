package com.ndt.capstone.mapper;


import com.ndt.capstone.dto.CategoryDTO;
import com.ndt.capstone.entity.CategoryEntity;


public class CategoryMapper {
    private CategoryMapper() {
    }


    public static CategoryDTO toDTO(CategoryEntity obj) {
        if (obj == null)
            return null;

        CategoryDTO dto = new CategoryDTO();

        dto.setName(obj.getName());
        return dto;
    }
}
