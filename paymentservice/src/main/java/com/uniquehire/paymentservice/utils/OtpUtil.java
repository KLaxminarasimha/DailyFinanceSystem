package com.uniquehire.paymentservice.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OtpUtil {

    private static final Map<Long, String> otpStore = new HashMap<>();

    public String generateOtp(Long paymentId) {
        String otp = String.valueOf((int)(Math.random()*9000)+1000);
        otpStore.put(paymentId, otp);
        return otp;
    }

    public boolean verifyOtp(Long paymentId, String otp) {
        return otp.equals(otpStore.get(paymentId));
    }
}