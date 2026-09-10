package com.ndt.capstone.mapper;


import com.ndt.capstone.dto.TagDTO;
import com.ndt.capstone.entity.TagEntity;


public class TagMapper {
    private TagMapper() {
    }


    public static TagDTO toDTO(TagEntity obj) {
        if (obj == null)
            return null;

        TagDTO dto = new TagDTO();

        dto.setName(obj.getName());
        return dto;
    }
}
