package com.ndt.capstone.spec;

import java.util.*;
import java.math.BigDecimal;


import jakarta.persistence.criteria.*;


import org.springframework.data.jpa.domain.Specification;


import com.ndt.capstone.entity.*;

import static com.ndt.capstone.utils.SpecificationUtils.*;


import com.ndt.capstone.dto.request.PriceRangeDTO;


public final class ProductSpec {
    private ProductSpec() {
    }


    private static Predicate hasFieldBetweenRange(
        String name,
        BigDecimal lowerBound,
        BigDecimal upperBound,
        Path<?> root,
        CriteriaBuilder cb
    ) {
        Path<BigDecimal> path = root.get(name);
        return upperBound != null ? cb.between(path, lowerBound, upperBound) : cb.greaterThanOrEqualTo(path, lowerBound);
    }


    public static Predicate hasNameContainingIgnoreCase(
        String val,
        Root<ProductEntity> root,
        CriteriaBuilder cb
    ) {
        return hasFieldContainingIgnoreCase("name", val, root, cb);
    }


    // product -> product_category (OneToMany) -> category (ManyToOne)
    private static Predicate hasCategoriesIn(
        Set<String> categories,
        Root<ProductEntity> root,
        CriteriaBuilder cb
    ) {
        if (categories == null || categories.isEmpty())
            return null;

        Join<ProductEntity, ProductCategoryEntity> productCategoryJoin = root.join("productCategories", JoinType.LEFT);
        Join<ProductCategoryEntity, CategoryEntity> categoryJoin = productCategoryJoin.join("category", JoinType.LEFT);
        return cb.or(
            categories.stream()
                .map(category -> hasFieldHavingEqualIgnoreCase("name", category, categoryJoin, cb))
                .toList()
        );
    }


    // product -> tag (OneToMany)
    private static Predicate hasTagsIn(
        Set<String> tags,
        Root<ProductEntity> root,
        CriteriaBuilder cb
    ) {
        if (tags == null || tags.isEmpty())
            return null;

        Join<ProductEntity, ProductTagEntity> productTagJoin = root.join("productTags", JoinType.LEFT);
        Join<ProductEntity, TagEntity> tagJoin = productTagJoin.join("tag", JoinType.LEFT);
        return cb.or(
            tags.stream()
                .map(t -> hasFieldHavingEqualIgnoreCase("name", t, tagJoin, cb))
                .toList()
        );
    }


    // product -> brand (OneToMany)
    private static Predicate hasBrandsIn(
        Set<String> brands,
        Root<ProductEntity> root,
        CriteriaBuilder cb
    ) {
        if (brands == null || brands.isEmpty())
            return null;

        Join<ProductEntity, BrandEntity> brandJoin = root.join("brand", JoinType.LEFT);
        return cb.or(
            brands.stream()
                .map(brand -> hasFieldHavingEqualIgnoreCase("name", brand, brandJoin, cb))
                .toList()
        );
    }


    private static Predicate hasPriceInRanges(
        List<PriceRangeDTO> priceRanges,
        Root<ProductEntity> root,
        CriteriaBuilder cb
    ) {
        if (priceRanges == null || priceRanges.isEmpty())
            return null;

        return cb.or(
            priceRanges.stream()
                .map(range -> hasFieldBetweenRange("price", range.getMinPrice(), range.getMaxPrice(), root, cb))
                .toList()
        );
    }


    public static Specification<ProductEntity> build(
        String name,
        Set<String> categories,
        Set<String> tags,
        Set<String> brands,
        List<PriceRangeDTO> priceRanges
    ) {
        return (root, query, cb) -> {
            query.distinct(true);

            List<Predicate> predicates = new java.util.ArrayList<>();

            addIfNotNull(predicates, hasNameContainingIgnoreCase(name, root, cb));
            addIfNotNull(predicates, hasCategoriesIn(categories, root, cb));
            addIfNotNull(predicates, hasTagsIn(tags, root, cb));
            addIfNotNull(predicates, hasBrandsIn(brands, root, cb));
            addIfNotNull(predicates, hasPriceInRanges(priceRanges, root, cb));
            return cb.and(predicates);
        };

    }
}
