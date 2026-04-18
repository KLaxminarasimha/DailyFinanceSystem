package com.dailyfinance.auth_service.service.impl;

import com.dailyfinance.auth_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Verification - Daily Finance");

        message.setText(
                "Hello,\n\n" +
                        "Your OTP is: " + otp + "\n" +
                        "This OTP is valid for 5 minutes.\n\n" +
                        "Thank you!"
        );

        mailSender.send(message);
    }
}