package com.ndt.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ProductCategoryId;
import com.ndt.capstone.entity.ProductCategoryEntity;


public interface ProductCategoryRepo extends JpaRepository<ProductCategoryEntity, ProductCategoryId> {
}