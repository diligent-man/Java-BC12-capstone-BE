package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import org.springframework.data.domain.*;

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

    private final Integer cacheDuration;


    public BrandServiceImpl(
        BrandRepository brandRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.brand.prefix:brand}") String brandPrefixCacheKey,
        @Value(value = "${cache.brand.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.brandRepository = brandRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.brandAllCacheKey = brandPrefixCacheKey + ":all";
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
                Duration.ofMillis(cacheDuration)
            );

            return brands;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }

    // @Override
    // public Page<ProductDTO> getPagedProducts(Pageable pageable) {
    //     return brandRepository
    //         .findAll(pageable)
    //         .map(ele -> ProductMapper.toDTO(ele, defaultImage));
    // }


    // @Override
    // public Page<ProductDTO> searchProductByName(String keyword, int pageNumber, int pageSize) {
    //     Pageable page = PageRequest.of(pageNumber, pageSize);
    //     return brandRepository.findByNameContainingIgnoreCase(keyword, page)
    //         .map(product -> ProductMapper.toDTO(product, defaultImage));
    // }
}
