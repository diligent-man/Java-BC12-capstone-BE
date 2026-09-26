package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.SizeDTO;
import com.ndt.capstone.repository.SizeRepository;
import com.ndt.capstone.service.contract.SizeService;


@Service
public class SizeServiceImpl implements SizeService {
    private final String sizeAllCacheKey;

    private final SizeRepository sizeRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer allCacheDuration;


    public SizeServiceImpl(
        SizeRepository sizeRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.size.prefix:size}") String cacheKeyPrefix,
        @Value(value = "${cache.size.all.cache-duration:60000}") Integer allCacheDuration
    ) {
        this.sizeRepository = sizeRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.allCacheDuration = allCacheDuration;

        // post-setup
        this.sizeAllCacheKey = cacheKeyPrefix + ":all";
    }


    @Override
    public List<SizeDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(sizeAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<SizeDTO> sizes = sizeRepository
                .findAll()
                .stream()
                .map(s -> {
                    SizeDTO dto = new SizeDTO();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    return dto;
                })
                .toList();

            // Caching
            redisTemplate.opsForValue().set(
                sizeAllCacheKey,
                objectMapper.writeValueAsString(sizes),
                Duration.ofMillis(allCacheDuration)
            );

            return sizes;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
