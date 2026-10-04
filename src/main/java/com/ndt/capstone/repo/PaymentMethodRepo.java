package com.ndt.capstone.repo;

import java.util.Optional;


import jakarta.validation.constraints.NotNull;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ndt.capstone.entity.PaymentMethodEntity;


@Repository
public interface PaymentMethodRepo extends JpaRepository<PaymentMethodEntity, Integer> {
    Optional<PaymentMethodEntity> findByNameIgnoringCase(@NotNull String name);
}
