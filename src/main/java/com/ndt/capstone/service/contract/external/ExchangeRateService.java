package com.ndt.capstone.service.contract.external;

import java.math.BigDecimal;


public interface ExchangeRateService {
    BigDecimal getExchangeRate(String toCurrency);
}
