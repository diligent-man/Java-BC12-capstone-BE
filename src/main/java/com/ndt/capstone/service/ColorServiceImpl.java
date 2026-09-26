package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.ColorDTO;
import com.ndt.capstone.entity.ColorEntity;
import com.ndt.capstone.repository.ColorRepository;
import com.ndt.capstone.service.contract.ColorService;


@Service
public class ColorServiceImpl implements ColorService {
    private final String colorAllCacheKey;

    private final ColorRepository colorRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer allCacheDuration;


    public ColorServiceImpl(
        ColorRepository colorRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.color.prefix:color}") String cacheKeyPrefix,
        @Value(value = "${cache.color.all.cache-duration:60000}") Integer allCacheDuration
    ) {
        this.colorRepository = colorRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.allCacheDuration = allCacheDuration;

        // post-setup
        this.colorAllCacheKey = cacheKeyPrefix + ":all";
    }


    @Override
    public List<ColorDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(colorAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<ColorDTO> colors = colorRepository
                .findAll()
                .stream()
                .map(c -> {
                    ColorDTO dto = new ColorDTO();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    return dto;
                })
                .toList();

            // Caching
            redisTemplate.opsForValue().set(
                colorAllCacheKey,
                objectMapper.writeValueAsString(colors),
                Duration.ofMillis(allCacheDuration)
            );

            return colors;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
