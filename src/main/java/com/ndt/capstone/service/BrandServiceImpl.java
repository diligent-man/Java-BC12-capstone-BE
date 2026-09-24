package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.BrandDTO;
import com.ndt.capstone.mapper.BrandMapper;
import com.ndt.capstone.repository.BrandRepository;
import com.ndt.capstone.service.contract.BrandService;


@Service
public class BrandServiceImpl implements BrandService {
    private final String brandAllCacheKey;

    private final BrandRepository brandRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer allCacheDuration;


    public BrandServiceImpl(
        BrandRepository brandRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.brand.prefix:brand}") String cacheKeyPrefix,
        @Value(value = "${cache.brand.all.cache-duration:60000}") Integer allCacheDuration
    ) {
        this.brandRepository = brandRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.allCacheDuration = allCacheDuration;

        // post-setup
        this.brandAllCacheKey = cacheKeyPrefix + ":all";
    }


    @Override
    public List<BrandDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(brandAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<BrandDTO> brands = brandRepository
                .findAll()
                .stream()
                .map(BrandMapper::toDTO)
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                brandAllCacheKey,
                objectMapper.writeValueAsString(brands),
                Duration.ofMillis(allCacheDuration)
            );

            return brands;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
