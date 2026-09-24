package com.ndt.capstone.config.payment;

import java.math.BigDecimal;


import jakarta.annotation.PostConstruct;


import lombok.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;


@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "payment.exchange")
public class ExchangeRateConfig {
    @Setter
    @Getter
    private long refreshInterval = 7200000;

    @Setter
    @Getter
    private String baseCurrency = "USD";

    @Setter
    @Getter
    private BigDecimal fallbackRate = new BigDecimal("25400");

    @Setter
    @Getter
    private String apiUrl = "https://api.exchangerate-api.com/v4/latest/";


    @Value("${cache.payment.exchange.prefix:exchange_rate}")
    private String cachePrefix;

    @Getter
    private String rateCacheKey;

    @PostConstruct
    private void init() {
        apiUrl += baseCurrency;
        this.rateCacheKey = cachePrefix + ":rates";
    }
}
