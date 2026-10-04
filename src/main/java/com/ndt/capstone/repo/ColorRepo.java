package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ColorEntity;


@Repository
public interface ColorRepo extends JpaRepository<ColorEntity, Integer> {
    Optional<ColorEntity> findByNameIgnoreCase(String name);
}
