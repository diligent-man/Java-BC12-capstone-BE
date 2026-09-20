package com.ndt.capstone.service.contract;

import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.request.auth.SignupRequest;


/** Handles credentials and token issuance. Lock state (Redis keys, TTLs, counters, admin vs. automatic locks) is its own concern */
public interface AuthService {
    String doSignIn(LoginRequest request);


    void doSignUp(SignupRequest request);


    void doSignOut(String token);
}
