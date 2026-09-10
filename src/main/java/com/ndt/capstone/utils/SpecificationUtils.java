package com.ndt.capstone.utils;

import java.util.List;


import jakarta.persistence.criteria.*;


public final class SpecificationUtils {
    private SpecificationUtils() {
    }


    public static void addIfNotNull(List<Predicate> predicates, Predicate predicate) {
        if (predicate != null) {
            predicates.add(predicate);
        }
    }


    public static Predicate hasFieldContainingIgnoreCase(
        String name,
        String val,
        Path<?> root,
        CriteriaBuilder cb
    ) {
        if (val == null || val.isBlank())
            return null;
        return cb.like(cb.lower(root.get(name)), String.format("%%%s%%", val.toLowerCase()));
    }


    public static Predicate hasFieldHavingEqualIgnoreCase(
        String name,
        String val,
        Path<?> root,
        CriteriaBuilder cb
    ) {
        if (val == null || val.isBlank())
            return null;
        return cb.equal(cb.lower(root.get(name)), String.format("%s", val.toLowerCase()));
    }
}
