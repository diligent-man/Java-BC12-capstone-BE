package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.BrandEntity;


@Repository
public interface BrandRepo extends JpaRepository<BrandEntity, Integer> {
    Optional<BrandEntity> findByNameIgnoreCase(String name);
}
