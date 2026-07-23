package com.ndt.capstone.service;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


import com.ndt.capstone.dto.UserDto;
import com.ndt.capstone.enums.exception.UserError;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.exception.user.UserException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDto getUserByEmail(String email) {
        return UserDto.fromEntity(userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UserException(UserError.NOT_FOUND))
        );
    }
}
