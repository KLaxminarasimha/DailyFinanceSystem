package com.example.customer.service.impl;

import com.example.customer.dto.KycDTO;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Kyc;
import com.example.customer.enums.KycStatus;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.KycRepository;
import com.example.customer.service.EmailService;
import com.example.customer.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    @Override
    public Kyc submitKycByUserId(Long userId, KycDTO dto) {

        Customer customer = customerRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Generate 6-digit OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        // If KYC already exists → update it
        Kyc kyc = kycRepository.findByCustomer(customer)
                .orElse(new Kyc());

        kyc.setAadhar(dto.getAadhar());
        kyc.setPan(dto.getPan());
        kyc.setEmail(dto.getEmail());
        kyc.setPhone(dto.getPhone());
        kyc.setIfsc(dto.getIfsc());
        kyc.setAccountNumber(dto.getAccountNumber());

        kyc.setOtp(otp);
        kyc.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        kyc.setKycStatus(KycStatus.PENDING);
        kyc.setCustomer(customer);

        // Send OTP
        emailService.sendOtp(dto.getEmail(), otp);

        return kycRepository.save(kyc);
    }

    @Override
    public String verifyOtpByUserId(Long userId, String otp) {

        Customer customer = customerRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Kyc kyc = kycRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("KYC not found"));

        // ❌ Invalid OTP
        if (!kyc.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // ❌ Expired OTP
        if (kyc.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // ✅ Success
        kyc.setKycStatus(KycStatus.VERIFIED);

        // Optional: clear OTP after success
        kyc.setOtp(null);
        kyc.setOtpExpiry(null);

        kycRepository.save(kyc);

        return "KYC Verified Successfully";
    }
}