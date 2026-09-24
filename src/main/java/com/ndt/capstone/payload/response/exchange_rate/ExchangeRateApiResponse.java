package com.ndt.capstone.payload.response.exchange_rate;

import java.util.Map;
import java.math.BigDecimal;


import lombok.Data;


@Data
public class ExchangeRateApiResponse {
    private String base;

    private String date;

    private Map<String, BigDecimal> rates;
}
