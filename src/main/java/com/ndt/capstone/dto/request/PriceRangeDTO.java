package com.ndt.capstone.dto.request;

import java.math.BigDecimal;


import jakarta.validation.constraints.Min;


import lombok.Data;


import com.ndt.capstone.annotation.ValidPriceRange;


@Data
@ValidPriceRange
public class PriceRangeDTO {
    @Min(value = 0, message = "Minimum price cannot be negative")
    private BigDecimal minPrice;

    @Min(value = 0, message = "Maximum price cannot be negative")
    private BigDecimal maxPrice;
}
