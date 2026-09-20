package com.ndt.capstone.repository;

import com.ndt.capstone.entity.BillingDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetailsEntity, Long> {
    Optional<BillingDetailsEntity> findByOrderId(Long orderId);
}