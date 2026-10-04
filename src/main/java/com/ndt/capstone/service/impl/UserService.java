package com.ndt.capstone.service.impl;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.mapper.UserMapper;
import com.ndt.capstone.repo.UserRepo;
import com.ndt.capstone.enums.exception.UserErrMsg;
import com.ndt.capstone.exception.user.UserException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;


    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        return UserMapper.toDTO(userRepo
            .findByEmail(email)
            .orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND))
        );
    }
}
