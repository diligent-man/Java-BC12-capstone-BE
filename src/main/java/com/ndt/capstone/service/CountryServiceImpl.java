package com.ndt.capstone.service;

import java.util.List;
import java.time.Duration;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.CountryDTO;
import com.ndt.capstone.mapper.CountryMapper;
import com.ndt.capstone.repository.CountryRepository;
import com.ndt.capstone.service.contract.CountryService;


@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    private final String countryAllCacheKey;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer allCacheDuration;


    public CountryServiceImpl(
        CountryRepository countryRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.country.prefix:country}") String cacheKeyPrefix,
        @Value(value = "${cache.country.all.cache-duration:60000}") Integer allCacheDuration
    ) {
        this.countryRepository = countryRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.allCacheDuration = allCacheDuration;

        // post-setup
        this.countryAllCacheKey = cacheKeyPrefix + ":all";
    }


    @Override
    public List<CountryDTO> getAllCountries() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(countryAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<CountryDTO> countries = countryRepository
                .findAll()
                .stream()
                .map(CountryMapper::toDTO)
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                countryAllCacheKey,
                objectMapper.writeValueAsString(countries),
                Duration.ofMillis(allCacheDuration)
            );

            return countries;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
