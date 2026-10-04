package com.ndt.capstone.service.impl;

import java.time.Duration;

import java.util.Arrays;
import java.util.function.Supplier;


import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;


import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;


import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import com.ndt.capstone.service.contract.CacheService;


@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper mapper;


    public <T> T getOrLoad(String key, Duration ttl, TypeReference<T> type, Supplier<T> loader) {
        T cached = read(key, type);
        if (cached != null)
            return cached;

        // domain exceptions propagate untouched
        T value = loader.get();
        if (value != null)
            write(key, value, ttl);
        return value;
    }


    public <T> T read(String key, TypeReference<T> type) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            return (json == null || json.isBlank()) ? null : mapper.readValue(json, type);
        } catch (Exception e) {
            log.warn("Cache read failed for {}", key, e);
            // fall back to the DB
            return null;
        }
    }


    public void write(String key, Object value, Duration ttl) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(value), ttl);
        } catch (Exception e) {
            log.warn("Cache write failed for {}", key, e);
        }
    }


    public void evictAfterCommit(String... keys) {
        Runnable evict = () -> {
            try {
                redisTemplate.delete(Arrays.asList(keys));
            } catch (Exception e) {
                log.warn("Cache eviction failed for {}", Arrays.toString(keys), e);
            }
        };

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    evict.run();
                }
            });
        } else {
            evict.run();
        }
    }

    /** Runs the action after the current transaction commits (immediately if there is none). */
    public void runAfterCommit(Runnable action) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() { action.run(); }
            });
        } else {
            action.run();
        }
    }
}
