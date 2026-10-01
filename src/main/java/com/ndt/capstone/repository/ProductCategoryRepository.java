package com.ndt.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ProductCategoryEntity;
import com.ndt.capstone.entity.ProductCategoryId;


public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, ProductCategoryId> {
}