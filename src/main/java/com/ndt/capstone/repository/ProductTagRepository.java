package com.ndt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ProductTagEntity;
import com.ndt.capstone.entity.ProductTagId;


public interface ProductTagRepository extends JpaRepository<ProductTagEntity, ProductTagId> {
}