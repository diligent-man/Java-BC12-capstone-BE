package com.ndt.capstone.service.contract;


public interface LoginAttemptService {
    void checkLock(String email);


    long recordFailedAttempt(String email);


    void resetFailedAttempts(String email);


    String getActiveSession(String email);


    void removeSession(String email);
}
