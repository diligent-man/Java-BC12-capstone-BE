package com.ndt.capstone.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;


import com.ndt.capstone.dto.UserDto;

import com.ndt.capstone.entity.RoleEntity;
import com.ndt.capstone.entity.UserEntity;

import com.ndt.capstone.payload.req.auth.SignupRequest;


public class UserMapper {
    private UserMapper() {
    }


    public static UserDto toDTO(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRoleName(user.getRole().getName());
        return dto;
    }


    public static UserEntity toEntity(
        SignupRequest req,
        PasswordEncoder passwordEncoder
    ) {
        RoleEntity role = new RoleEntity();
        role.setId(3);

        UserEntity obj = new UserEntity();
        obj.setEmail(req.getEmail());
        obj.setPassword(passwordEncoder.encode(req.getPassword()));
        obj.setFullName(req.getFullName());
        obj.setRole(role);
        return obj;
    }
}
