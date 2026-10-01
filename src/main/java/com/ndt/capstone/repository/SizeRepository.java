package com.ndt.capstone.repository;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.SizeEntity;


@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {
    Optional<SizeEntity> findByNameContainingIgnoreCase(String name);
}
