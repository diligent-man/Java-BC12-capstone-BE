package com.ndt.capstone.repo;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ndt.capstone.entity.OrderVariantId;
import com.ndt.capstone.entity.OrderVariantEntity;


@Repository
public interface OrderVariantRepo extends JpaRepository<OrderVariantEntity, OrderVariantId> {
    List<OrderVariantEntity> findByOrder_Id(Long orderId);

    @Query("""
        SELECT ov FROM order_variant ov
        JOIN FETCH ov.variant v
        LEFT JOIN FETCH v.product p
        LEFT JOIN FETCH p.brand b
        LEFT JOIN FETCH v.color c
        LEFT JOIN FETCH v.size s
        WHERE ov.order.id IN :orderIds
        """)
    List<OrderVariantEntity> findByOrderIdInWithDetails(@Param("orderIds") List<Long> orderIds);
}
