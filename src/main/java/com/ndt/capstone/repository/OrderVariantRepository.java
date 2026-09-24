package com.ndt.capstone.repository;

import java.util.List;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ndt.capstone.entity.OrderVariantId;
import com.ndt.capstone.entity.OrderVariantEntity;


@Repository
public interface OrderVariantRepository extends JpaRepository<OrderVariantEntity, OrderVariantId> {
    List<OrderVariantEntity> findByOrder_Id(Long orderId);
}
