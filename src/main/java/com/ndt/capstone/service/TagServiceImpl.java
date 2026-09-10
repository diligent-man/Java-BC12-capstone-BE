package com.ndt.capstone.service;

import java.time.Duration;
import java.util.List;


import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;


import com.ndt.capstone.dto.TagDTO;
import com.ndt.capstone.mapper.TagMapper;
import com.ndt.capstone.repository.TagRepository;
import com.ndt.capstone.service.contract.TagService;


@Service
public class TagServiceImpl implements TagService {
    private final String tagAllCacheKey;

    private final TagRepository tagRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    private final Integer cacheDuration;


    public TagServiceImpl(
        TagRepository tagRepository,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper,
        @Value(value = "${cache.tag.prefix:tag}") String tagPrefixCacheKey,
        @Value(value = "${cache.tag.all.cache-duration:60000}") Integer cacheDuration
    ) {
        this.tagRepository = tagRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheDuration = cacheDuration;

        // post-setup
        this.tagAllCacheKey = tagPrefixCacheKey + ":all";
    }


    @Override
    public List<TagDTO> getAll() {
        try {
            // Read cache
            String cache = redisTemplate.opsForValue().get(tagAllCacheKey);

            if (cache != null && !cache.isBlank()) {
                return objectMapper.readValue(
                    cache,
                    new TypeReference<>() {
                    }
                );
            }

            // No cache -> Read db
            List<TagDTO> tags = tagRepository
                .findAll()
                .stream()
                .map(TagMapper::toDTO)
                .toList();


            // Caching
            redisTemplate.opsForValue().set(
                tagAllCacheKey,
                objectMapper.writeValueAsString(tags),
                Duration.ofMillis(cacheDuration)
            );

            return tags;
        } catch (Exception e) {
            throw new RuntimeException("Redis Cache Error", e);
        }
    }
}
