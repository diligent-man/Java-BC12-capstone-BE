package com.ndt.capstone.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ndt.capstone.entity.BrandEntity;


@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
}
