package com.ndt.capstone.repository;

import java.util.List;


import com.ndt.capstone.entity.PaymentStatusEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import com.ndt.capstone.entity.OrderEntity;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = """
            SELECT o
            FROM orders o
            WHERE o.status.id = 1 AND
                  o.createDate <= CURRENT_TIMESTAMP - :pendingTimeout SECOND
        """)
    List<OrderEntity> findPendingOlderThan(@Param("pendingTimeout") Integer pendingTimeout);


    @Modifying
    @Query("""
        UPDATE orders o SET o.status = :cancelled
        WHERE o.id = :id AND o.status.name = 'PENDING'
        """)
    int cancelIfPending(@Param("id") Long id, @Param("cancelled") PaymentStatusEntity cancelled);
}
