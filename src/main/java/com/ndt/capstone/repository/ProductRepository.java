package com.ndt.capstone.repository;

import com.ndt.capstone.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    // Thêm dòng này: Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường) + phân trang
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
