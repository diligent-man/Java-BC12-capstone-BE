package com.ndt.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ProductTagId;
import com.ndt.capstone.entity.ProductTagEntity;


public interface ProductTagRepo extends JpaRepository<ProductTagEntity, ProductTagId> {
}