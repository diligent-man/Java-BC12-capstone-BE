package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.CategoryDTO;
import com.ndt.capstone.mapper.CategoryMapper;
import com.ndt.capstone.repository.CategoryRepository;
import com.ndt.capstone.service.contract.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final String categoryAllCacheKey;

    private final CategoryRepository categoryRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer cacheDuration;


    public CategoryServiceImpl(
        CategoryRepository categoryRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.category.prefix:category}") String categoryPrefixCacheKey,
        @Value(value = "${cache.category.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.categoryAllCacheKey = categoryPrefixCacheKey + ":all";
    }


    @Override
    public List<CategoryDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(categoryAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<CategoryDTO> categories = categoryRepository
                .findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                categoryAllCacheKey,
                objectMapper.writeValueAsString(categories),
                Duration.ofMillis(cacheDuration)
            );

            return categories;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
