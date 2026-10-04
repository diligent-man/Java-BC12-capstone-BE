package com.ndt.capstone.service.impl;

import java.util.*;

import java.math.BigDecimal;

import java.time.Duration;
import java.time.Instant;


import jakarta.annotation.PostConstruct;


import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.scheduling.annotation.Scheduled;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import com.ndt.capstone.config.props.payment.ExchangeRateProps;
import com.ndt.capstone.enums.exception.ExchangeRateErrMsg;
import com.ndt.capstone.service.contract.external.ExchangeRateService;
import com.ndt.capstone.exception.exchange_rate.ExchangeRateException;
import com.ndt.capstone.payload.resp.exchange_rate.ExchangeRateApiResponse;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final RestClient restClient;

    private final ExchangeRateProps config;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private volatile Instant lastFetched;


    @PostConstruct
    public void init() {
        refreshRate();
    }


    @Override
    public BigDecimal getExchangeRate(String toCurrency) {
        Map<String, BigDecimal> rateMap = readCachedRate();
        BigDecimal rate = config.fallbackRate();

        if (!rateMap.isEmpty()) {
            rate = rateMap.get(toCurrency);
        } else {
            log.warn("No exchange rate cached for {}, using fallback rate {}", toCurrency, rate.toString());
        }
        return rate;
    }


    @Scheduled(fixedRateString = "${payment.ExchangeRateConfig.refresh-interval:7200000}")
    private void scheduledRefresh() {
        refreshRate();
    }


    private synchronized void refreshRate() {
        try {
            ExchangeRateApiResponse resp = restClient
                .get()
                .uri(config.apiUrl())
                .retrieve()
                .body(ExchangeRateApiResponse.class);

            if (resp == null)
                throw new ExchangeRateException(ExchangeRateErrMsg.API_FETCH_FAIL);

            if (resp.getRates() == null || resp.getRates().isEmpty())
                throw new ExchangeRateException(ExchangeRateErrMsg.NO_RESULT);

            Map<String, BigDecimal> fetched = resp.getRates();

            redisTemplate.opsForValue().set(
                config.rateCacheKey(),
                objectMapper.writeValueAsString(fetched),
                Duration.ofMillis(config.refreshInterval() * 2)
            );

            lastFetched = Instant.now();
            log.info("{} {}", ExchangeRateErrMsg.API_FETCH_SUCESS.getErrorMsg(), lastFetched);
        } catch (ExchangeRateException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error("Get unknown exception. Message: {}", e.getMessage(), e);
        }
    }


    private Map<String, BigDecimal> readCachedRate() {
        try {
            String cache = redisTemplate.opsForValue().get(config.rateCacheKey());

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(cache, new TypeReference<>() {
                });
            }
        } catch (Exception e) {
            log.error("Failed to read exchange rate from Redis", e);
        }
        return Collections.emptyMap();
    }
}
