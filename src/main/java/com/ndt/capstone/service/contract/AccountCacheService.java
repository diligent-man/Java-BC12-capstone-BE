package com.ndt.capstone.service.contract;


public interface AccountCacheService {
    void markLocked(String email);


    void clearLock(String email);
}
