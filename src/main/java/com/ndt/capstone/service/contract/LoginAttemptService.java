package com.ndt.capstone.service.contract;

import com.ndt.capstone.dto.auth.LoginAttemptDTO;


public interface LoginAttemptService {
    void checkLock(String email);


    LoginAttemptDTO recordFailedAttempt(String email);


    void resetFailedAttempts(String email);


    String getActiveSession(String email);


    void removeSession(String email);
}
