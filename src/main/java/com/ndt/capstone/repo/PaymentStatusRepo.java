package com.ndt.capstone.repo;

import java.util.Optional;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.PaymentStatusEntity;


@Repository
public interface PaymentStatusRepo extends JpaRepository<PaymentStatusEntity, Integer> {
    Optional<PaymentStatusEntity> findByNameIgnoringCase(String name);
}
