package com.ndt.capstone.utils;

import lombok.Getter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Getter
@Component
public final class AccountCacheKeys {
    private final String failCountKey;

    private final String lockStatusKey;

    private final String wasLockedKey;

    private final String sessionKey;


    public AccountCacheKeys(
        @Value(value = "${cache.account.prefix:account}") String cacheKeyPrefix
    ) {
        this.failCountKey = cacheKeyPrefix + ":fail_count:";
        this.lockStatusKey = cacheKeyPrefix + ":lock_status:";
        this.wasLockedKey = cacheKeyPrefix + ":was_locked:";
        this.sessionKey = cacheKeyPrefix + ":session:";
    }
}
