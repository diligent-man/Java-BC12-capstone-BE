package com.ndt.capstone.repository;

import com.ndt.capstone.entity.OrderVariantEntity;
import com.ndt.capstone.entity.OrderVariantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderVariantRepository extends JpaRepository<OrderVariantEntity, OrderVariantId> {
    List<OrderVariantEntity> findByOrder_Id(Long orderId);
}