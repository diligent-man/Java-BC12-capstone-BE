package com.ndt.capstone.repository;

import java.util.List;


import com.ndt.capstone.entity.PaymentStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = """
        SELECT o FROM orders o
        JOIN FETCH o.status s
        LEFT JOIN FETCH o.payment p
        WHERE o.user.id = :userId
        ORDER BY o.createDate DESC
        """,
            countQuery = """
        SELECT COUNT(o) FROM orders o WHERE o.user.id = :userId
        """)
    Page<OrderEntity> findByUserIdWithStatusAndPayment(@Param("userId") Long userId, Pageable pageable);
}
