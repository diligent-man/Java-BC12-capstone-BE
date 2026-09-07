package com.ndt.capstone.repository;

import com.ndt.capstone.entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VariantRepository extends JpaRepository<ProductVariantEntity, Long> {
}
