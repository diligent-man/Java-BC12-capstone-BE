package com.ndt.capstone.service.impl;

import java.util.List;


import lombok.RequiredArgsConstructor;


import org.jspecify.annotations.NonNull;


import org.springframework.data.redis.core.*;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.types.Expiration;


import com.ndt.capstone.enums.account.AccountLock;
import com.ndt.capstone.config.props.cache.AccountCacheProps;
import com.ndt.capstone.service.contract.AccountCacheService;


@Service
@RequiredArgsConstructor
public class AccountCacheServiceImpl implements AccountCacheService {
    private final StringRedisTemplate redis;

    private final AccountCacheProps cachProps;


    public void markLocked(String email) {
        String sessionKey = cachProps.sessionKey() + email;
        String lockedKey = cachProps.lockStatusKey() + email;
        String wasLockedKey = cachProps.wasLockedKey() + email;

        redis.execute(new SessionCallback<List<Object>>() {
            @Override
            public <K, V> List<Object> execute(@NonNull RedisOperations<K, V> ops) {
                @SuppressWarnings("unchecked")
                RedisOperations<String, String> strOps = (RedisOperations<String, String>) ops;

                strOps.multi();
                strOps.opsForValue().set(lockedKey, AccountLock.TEMP.name(), Expiration.keepTtl());
                strOps.opsForValue().set(wasLockedKey, Boolean.TRUE.toString());
                strOps.delete(sessionKey);
                return strOps.exec();
            }
        });
    }


    public void clearLock(String email) {
        redis.delete(List.of(
            cachProps.lockStatusKey() + email,
            cachProps.wasLockedKey() + email,
            cachProps.failCountKey() + email
        ));
    }
}
