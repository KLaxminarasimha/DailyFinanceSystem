package com.dailyfinance.auth_service.service;

public interface EmailService {
    void sendOtp(String to, String otp);
}