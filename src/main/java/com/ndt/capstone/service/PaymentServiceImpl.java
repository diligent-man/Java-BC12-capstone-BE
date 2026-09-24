package com.ndt.capstone.service;

import java.time.Duration;
import java.util.List;


import com.ndt.capstone.dto.BrandDTO;
import com.ndt.capstone.dto.payment.PaymentMethodDTO;
import com.ndt.capstone.mapper.BrandMapper;
import com.ndt.capstone.mapper.payment.PaymentMethodMapper;
import com.ndt.capstone.repository.BrandRepository;
import com.ndt.capstone.repository.PaymentMethodRepository;
import com.ndt.capstone.service.contract.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@Service
public class PaymentServiceImpl implements PaymentService {
    private final String paymentMathodAllCacheKey;

    private final PaymentMethodRepository paymentMethodRepo;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer paymentMethodAllCacheDuration;


    public PaymentServiceImpl(
        PaymentMethodRepository paymentMethodRepo,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.payment.method.prefix:payment_method}") String paymentMethodCacheKeyPrefix,
        @Value(value = "${cache.payment.method.all.cache-duration:60000}") Integer paymentMethodAllCacheDuration
    ) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;

        this.paymentMethodAllCacheDuration = paymentMethodAllCacheDuration;

        // post-setup
        this.paymentMathodAllCacheKey = paymentMethodCacheKeyPrefix + ":all";
    }


    public List<PaymentMethodDTO> getPaymentMethods() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(paymentMathodAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<PaymentMethodDTO> paymentMethods = paymentMethodRepo
                .findAll()
                .stream()
                .map(PaymentMethodMapper::toDTO)
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                paymentMathodAllCacheKey,
                objectMapper.writeValueAsString(paymentMethods),
                Duration.ofMillis(paymentMethodAllCacheDuration)
            );

            return paymentMethods;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
