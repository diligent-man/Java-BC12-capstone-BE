package com.ndt.capstone.service;

import java.util.List;


import lombok.RequiredArgsConstructor;


import org.springframework.data.redis.core.*;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import com.ndt.capstone.enums.account.AccountLock;
import com.ndt.capstone.enums.account.AccountStatus;

import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.utils.AccountCacheKeys;
import com.ndt.capstone.repository.UserRepository;
import com.ndt.capstone.enums.exception.UserErrMsg;
import com.ndt.capstone.exception.user.UserException;
import com.ndt.capstone.service.contract.AccountService;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;

    private final StringRedisTemplate redisTemplate;

    private final AccountCacheKeys accountCacheKeys;


    @Override
    @Transactional
    public void lockAccount(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));
        user.setStatus(AccountStatus.LOCKED.name());

        String email = user.getEmail();
        String sessionKey = accountCacheKeys.getSessionKey() + email;
        String lockedKey = accountCacheKeys.getLockStatusKey() + email;
        String wasLockedKey = accountCacheKeys.getWasLockedKey() + email;

        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    redisTemplate.execute(new SessionCallback<List<Object>>() {
                        @Override
                        public <K, V> List<Object> execute(RedisOperations<K, V> ops) {
                            ops.multi();
                            redisTemplate.opsForValue().set(lockedKey, AccountLock.TEMP.name(), Expiration.keepTtl());
                            redisTemplate.opsForValue().set(wasLockedKey, Boolean.TRUE.toString());
                            redisTemplate.delete(sessionKey);
                            return ops.exec();
                        }
                    });
                }
            });
    }


    @Override
    @Transactional
    public void unlockAccount(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));
        user.setStatus(AccountStatus.ACTIVE.name());

        String email = user.getEmail();
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    redisTemplate.delete(List.of(
                        accountCacheKeys.getLockStatusKey() + email,
                        accountCacheKeys.getWasLockedKey() + email,
                        accountCacheKeys.getFailCountKey() + email
                    ));
                }
            });
    }
}
