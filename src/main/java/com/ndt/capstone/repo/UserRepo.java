package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.UserEntity;


@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);


    boolean existsByEmail(String email);
}
