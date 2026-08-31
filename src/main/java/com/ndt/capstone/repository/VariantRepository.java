package com.ndt.capstone.repository;

import com.ndt.capstone.entity.VariantProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<VariantProductEntity, Integer> {
}
