package com.dailyfinance.auth_service.dto.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {

    private String email;
    private String otp;
}
