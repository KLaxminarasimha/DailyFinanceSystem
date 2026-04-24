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
    public Kyc submitKyc(Long customerId, KycDTO dto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        Kyc kyc = new Kyc();
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

        emailService.sendOtp(dto.getEmail(), otp); // for testing

        return kycRepository.save(kyc);
    }

    @Override
    public String verifyOtp(Long customerId, String otp) {

        Kyc kyc = kycRepository.findAll().stream()
                .filter(k -> k.getCustomer().getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("KYC not found"));

        if (!kyc.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (kyc.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        kyc.setKycStatus(KycStatus.VERIFIED);
        kycRepository.save(kyc);

        return "KYC Verified Successfully";
    }
}