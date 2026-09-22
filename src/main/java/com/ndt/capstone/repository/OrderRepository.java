package com.ndt.capstone.repository;

import com.ndt.capstone.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM orders WHERE id_status =1 AND TIMESTAMPDIFF(SECOND, create_date, NOW()) >= 120", nativeQuery = true)
    List<OrderEntity> findExpiredOrders();
}