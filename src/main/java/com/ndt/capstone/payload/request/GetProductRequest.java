package com.ndt.capstone.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetProductRequest {
    private String keyword;
    private int pageNumber;
    private int pageSize;

}
