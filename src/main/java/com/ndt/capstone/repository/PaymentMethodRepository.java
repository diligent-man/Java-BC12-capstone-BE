package com.ndt.capstone.repository;

import java.util.Optional;


import jakarta.validation.constraints.NotNull;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.PaymentMethodEntity;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Integer> {
    Optional<PaymentMethodEntity> findByName(@NotNull String paymentMethodName);
}
