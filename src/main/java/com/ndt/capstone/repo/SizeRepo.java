package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.SizeEntity;


@Repository
public interface SizeRepo extends JpaRepository<SizeEntity, Integer> {
    Optional<SizeEntity> findByNameIgnoreCase(String name);
}
