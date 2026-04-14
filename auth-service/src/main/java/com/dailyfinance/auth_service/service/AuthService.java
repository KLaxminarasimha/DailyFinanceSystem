package com.dailyfinance.auth_service.service;


import com.dailyfinance.auth_service.dto.request.LoginRequest;
import com.dailyfinance.auth_service.dto.request.RegisterRequest;
import com.dailyfinance.auth_service.dto.response.AuthResponse;
import com.dailyfinance.auth_service.dto.response.RegisterResponse;
import com.dailyfinance.auth_service.dto.response.VerifyResponse;

public interface AuthService {


    RegisterResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    VerifyResponse verifyToken(String token);
}
