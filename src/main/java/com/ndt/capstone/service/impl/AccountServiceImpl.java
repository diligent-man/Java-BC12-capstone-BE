package com.ndt.capstone.service.impl;


import com.ndt.capstone.service.contract.CacheService;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ndt.capstone.service.contract.*;

import com.ndt.capstone.entity.UserEntity;
import com.ndt.capstone.repo.UserRepo;
import com.ndt.capstone.exception.user.UserException;

import com.ndt.capstone.enums.exception.UserErrMsg;
import com.ndt.capstone.enums.account.AccountStatus;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserRepo userRepo;

    private final CacheService cacheService;

    private final AccountCacheService accountCacheService;


    @Override
    @Transactional
    public void lockAccount(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));
        user.setStatus(AccountStatus.LOCKED.name());

        String email = user.getEmail();
        accountCacheService.markLocked(email);
    }


    @Override
    @Transactional
    public void unlockAccount(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserException(UserErrMsg.NOT_FOUND));
        user.setStatus(AccountStatus.ACTIVE.name());

        String email = user.getEmail();
        cacheService.runAfterCommit(() -> accountCacheService.markLocked(email));
    }
}
