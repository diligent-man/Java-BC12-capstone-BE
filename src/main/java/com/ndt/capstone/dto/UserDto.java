package com.ndt.capstone.dto;

import lombok.Data;

import com.ndt.capstone.entity.UserEntity;


@Data
public class UserDto {
    private Long id;

    private String email;

    private String fullName;

    private String roleName;


    public static UserDto fromEntity(UserEntity user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.fullName = user.getFullName();
        dto.roleName = user.getRole().getName();
        return dto;
    }
}
