package com.ndt.capstone.payload.request.product;

import java.util.*;


import jakarta.validation.Valid;


import lombok.Data;

import com.ndt.capstone.dto.request.PriceRangeDTO;


@Data
public class ProductFilterRequest {
    String name;

    Set<String> categories = new HashSet<>();

    Set<String> tags = new HashSet<>();

    Set<String> brands = new HashSet<>();

    List<@Valid PriceRangeDTO> priceRanges = new ArrayList<>();
}
