package com.ndt.capstone.service.impl;

import java.time.Duration;

import java.util.List;


import lombok.NonNull;


import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import com.ndt.capstone.enums.account.AccountLock;
import com.ndt.capstone.enums.exception.AuthErrMsg;

import com.ndt.capstone.config.props.cache.AccountCacheProps;
import com.ndt.capstone.exception.auth.AuthException;
import com.ndt.capstone.service.contract.LoginAttemptService;


@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private final StringRedisTemplate redisTemplate;

    private final AccountCacheProps accountCacheProperties;

    // cache key TTL
    private final long maxAttempts;

    private final long lockDuration;


    public LoginAttemptServiceImpl(
        StringRedisTemplate redisTemplate,
        AccountCacheProps accountCacheProperties,
        @Value("${auth.max-attempt:3}") long maxAttempts,
        @Value("${auth.lock-duration:900000}") int lockDuration
    ) {
        this.redisTemplate = redisTemplate;
        this.accountCacheProperties = accountCacheProperties;

        this.maxAttempts = maxAttempts;
        this.lockDuration = lockDuration;
    }


    /**
     * @return "TEMP", "PERMANENT", hoặc null (không bị khoá)
     * */
    private String getLockType(String email) {
        return redisTemplate.opsForValue().get(accountCacheProperties.lockStatusKey() + email);
    }


    public void checkLock(String email) {
        String lockType = getLockType(email);
        long remainingTime = redisTemplate.getExpire(accountCacheProperties.lockStatusKey() + email);

        if (AccountLock.PERMANENT.name().equals(lockType))
            throw new AuthException(AuthErrMsg.ACCOUNT_PERMANENTLY_LOCKED);

        if (AccountLock.TEMP.name().equals(lockType))
            throw new AuthException(
                AuthErrMsg.ACCOUNT_TEMP_LOCKED,
                String.format("%s %ds", AuthErrMsg.ACCOUNT_TEMP_LOCKED.getErrorMsg(), remainingTime)
            );
    }


    /**
     * Trigger when user enter incorrect login credential
     * @return num of remaining login attempts see {@code maxAttempts} field
     */
    public long recordFailedAttempt(String email) {
        String failKey = accountCacheProperties.failCountKey() + email;
        String lockedKey = accountCacheProperties.lockStatusKey() + email;
        String wasLockedKey = accountCacheProperties.wasLockedKey() + email;

        long currentAttempts = redisTemplate.opsForValue().increment(failKey);
        if (currentAttempts == maxAttempts) {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(wasLockedKey))) {
                redisTemplate.opsForValue().set(lockedKey, AccountLock.PERMANENT.name());
                redisTemplate.delete(failKey);
            } else {
                redisTemplate.execute(new SessionCallback<List<Object>>() {
                    @Override
                    public <K, V> List<Object> execute(@NonNull RedisOperations<K, V> ops) {
                        ops.multi();
                        redisTemplate.opsForValue().set(lockedKey, AccountLock.TEMP.name(), Duration.ofMillis(lockDuration));
                        redisTemplate.opsForValue().set(wasLockedKey, Boolean.TRUE.toString());
                        return ops.exec();
                    }
                });
            }
        }
        // always add 1 for last time
        return maxAttempts - currentAttempts + 1;
    }


    public void resetFailedAttempts(String email) {
        redisTemplate.delete(List.of(
            accountCacheProperties.failCountKey() + email,
            accountCacheProperties.wasLockedKey() + email
        ));
    }


    public void saveSession(String email, String accessToken, long expiration) {
        redisTemplate.opsForValue().set(
            accountCacheProperties.sessionKey() + email,
            accessToken,
            Duration.ofMillis(expiration)
        );
    }


    /**
     * @return current token or null in case of no login session
     * */
    public String getActiveSession(String email) {
        return redisTemplate.opsForValue().get(accountCacheProperties.sessionKey() + email);
    }


    public void removeSession(String email) {
        redisTemplate.delete(accountCacheProperties.sessionKey() + email);
    }
}
