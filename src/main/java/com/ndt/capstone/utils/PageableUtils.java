package com.ndt.capstone.utils;


import org.springframework.data.domain.*;


public final class PageableUtils {
    private PageableUtils() {
    }


    public static Pageable withDefaultSort(Pageable pageable, Sort defaultSort) {
        return pageable.getSort().isUnsorted()
            ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort)
            : pageable;
    }
}
