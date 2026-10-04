package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.CategoryEntity;


@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameIgnoringCase(String name);
}
