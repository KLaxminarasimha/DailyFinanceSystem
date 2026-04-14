package com.dailyfinance.auth_service.service;


import com.dailyfinance.auth_service.dto.request.LoginRequest;
import com.dailyfinance.auth_service.dto.request.RegisterRequest;

public interface AuthService {

    Object register(RegisterRequest request);

    Object login(LoginRequest request);
}
