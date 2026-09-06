package com.ndt.capstone.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.ProductEntity;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Thêm dòng này: Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường) + phân trang
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
