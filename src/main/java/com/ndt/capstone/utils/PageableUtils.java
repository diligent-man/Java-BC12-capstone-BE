package com.ndt.capstone.utils;


import org.springframework.data.domain.*;


public final class PageableUtils {
    private PageableUtils() {
    }


    public static Pageable withDefaultSort(Sort defaultSort, Pageable pageable) {
        return pageable.getSort().isUnsorted()
            ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort)
            : pageable;
    }
}
