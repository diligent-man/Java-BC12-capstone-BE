package com.ndt.capstone.payload.response;

import java.util.List;


import lombok.*;


import org.springframework.data.domain.Page;


@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean lastPage;


    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
            .content(page.getContent())
            .page(page.getNumber())
            .size(page.getSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .lastPage(page.isLast())
            .build();
    }
}
