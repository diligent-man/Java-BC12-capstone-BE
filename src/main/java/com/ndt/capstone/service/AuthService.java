package com.ndt.capstone.service;

import com.ndt.capstone.payload.request.auth.LoginRequest;


public interface AuthService {
    String doLogin(LoginRequest request);
}
