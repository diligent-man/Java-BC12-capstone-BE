package com.ndt.capstone.repository;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.PaymentStatusEntity;


@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, Integer> {
    Optional<PaymentStatusEntity> findByName(String name);
}
