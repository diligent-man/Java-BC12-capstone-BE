package com.ndt.capstone.service.contract;

import java.time.Duration;
import java.util.function.Supplier;


import tools.jackson.core.type.TypeReference;


public interface CacheService {
    <T> T getOrLoad(String key, Duration ttl, TypeReference<T> type, Supplier<T> loader);


    <T> T read(String key, TypeReference<T> type);


    void write(String key, Object value, Duration ttl);


    void evictAfterCommit(String... keys);


    void runAfterCommit(Runnable action);
}
