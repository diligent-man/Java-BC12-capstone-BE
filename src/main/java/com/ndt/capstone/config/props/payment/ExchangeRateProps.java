package com.ndt.capstone.config.props.payment;

import java.math.BigDecimal;


import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "payment.exchange")
public record ExchangeRateProps(
    @DefaultValue("7200000") long refreshInterval,
    @DefaultValue("USD") String baseCurrency,
    @DefaultValue("25400") BigDecimal fallbackRate,
    @DefaultValue("https://api.exchangerate-api.com/v4/latest/") String apiUrl,
    @DefaultValue("exchange_rate") String cachePrefix
) {
    public ExchangeRateProps {
        apiUrl = apiUrl + baseCurrency;
    }


    public String rateCacheKey() {
        return cachePrefix + ":rates";
    }
}
