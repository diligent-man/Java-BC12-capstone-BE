package com.ndt.capstone.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.*;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import com.ndt.capstone.entity.ProductEntity;
import com.ndt.capstone.projection.product.ProductVariantRow;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    @Query("""
        SELECT
            p.name AS name, p.description AS description, p.information AS information, p.price AS basePrice,
            b.name AS brand,
            category.name AS categoryName,
            t.name AS tagName,
            v.sku AS sku, v.quantity AS quantity, v.price AS variantPrice, v.images AS images,
            c.name AS colorName,
            s.name AS sizeName
        FROM product p
            JOIN brand b ON b.id = p.brand.id
        
            JOIN product_category AS pc ON pc.product.id  = p.id
            LEFT JOIN category ON category.id = pc.category.id
        
            JOIN product_tag AS pt ON pt.product.id = p.id
            LEFT JOIN tag AS t ON t.id = pt.tag.id
        
            LEFT JOIN variant v ON v.product.id = p.id
            LEFT JOIN color c ON c.id = v.color.id
            LEFT JOIN size s ON s.id = v.size.id
        WHERE p.name = :name
        """)
    List<ProductVariantRow> findProductDetailByName(@Param("name") String name);


    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
