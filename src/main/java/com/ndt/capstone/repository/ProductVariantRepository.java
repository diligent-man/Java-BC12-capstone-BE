package com.ndt.capstone.repository;

import java.util.Optional;


import jakarta.validation.constraints.NotNull;


import org.springframework.data.jpa.repository.*;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import com.ndt.capstone.entity.ProductVariantEntity;


@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    Optional<ProductVariantEntity> findBySku(@NotNull Long sku);


    @Modifying
    @Query("""
            UPDATE variant v
            SET v.quantity = v.quantity - :qty
            WHERE v.sku = :sku AND v.quantity >= :qty
        """)
    int decreaseQuantity(@Param("sku") Long sku, @Param("qty") int qty);


    @Modifying
    @Query("""
        UPDATE variant v
        SET v.quantity = v.quantity + :qty
        WHERE v.sku = :sku
        """)
    int increaseQuantity(@Param("sku") Long sku, @Param("qty") int qty);
}
