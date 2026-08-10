package com.ndt.capstone.service;

import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.request.auth.SignupRequest;


public interface AuthService {
    String doLogin(LoginRequest request);
    void doSignup(SignupRequest request);
    void doLogout(String token);
}
