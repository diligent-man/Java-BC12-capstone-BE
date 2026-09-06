package com.ndt.capstone.service.contract;

import com.ndt.capstone.payload.request.auth.LoginRequest;
import com.ndt.capstone.payload.request.auth.SignupRequest;


public interface AuthService {
    String doLogin(LoginRequest request);


    void doSignUp(SignupRequest request);


    void doLogout(String token);
}
