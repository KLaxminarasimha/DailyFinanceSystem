package com.dailyfinance.auth_service.service.impl;

import com.dailyfinance.auth_service.dto.request.*;
import com.dailyfinance.auth_service.dto.response.*;
import com.dailyfinance.auth_service.entity.*;
import com.dailyfinance.auth_service.exception.*;
import com.dailyfinance.auth_service.repository.*;
import com.dailyfinance.auth_service.security.JwtUtil;
import com.dailyfinance.auth_service.service.AuthService;
import com.dailyfinance.auth_service.service.EmailService;
import com.dailyfinance.auth_service.utils.AuditUtil;
import com.dailyfinance.auth_service.utils.OtpUtil;
import com.dailyfinance.auth_service.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final EmailService emailService;

    //REGISTER
    @Override
    public RegisterResponse register(RegisterRequest request) {
        validationUtil.validateRegister(request);
        validationUtil.validatePassword(request.getPassword());
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .agentArea(request.getAgentArea())
                .build();
        userRepository.save(user);
        //Audit Log
        auditLogRepository.save(
                AuditUtil.createLog(user, "REGISTER", "USERS")
        );
        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole())
                .agentArea(user.getAgentArea())
                .createdAt(user.getCreatedAt())
                .build();
    }

    //LOGIN
    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isVerified()) {
            throw new UnauthorizedException("Please verify your account");
        }

        validationUtil.validateLogin(request);
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
        auditLogRepository.save(
                AuditUtil.createLog(user, "LOGIN", "USERS")
        );
        return AuthResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .token(token)
                .expiresIn(86400L)
                .build();
    }

    //VERIFY
    @Override
    public VerifyResponse verifyToken(String token) {

        try {
            String email = jwtUtil.extractEmail(token);
            Long userId = jwtUtil.extractUserId(token);
            String role = jwtUtil.extractRole(token);

            return VerifyResponse.builder()
                    .userId(userId)
                    .email(email)
                    .role(Role.valueOf(role))
                    .isValid(true)
                    .build();

        } catch (Exception e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }
    @Override
    public RegisterResponse registerCustomer(CustomerRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ResourceAlreadyExistsException("Phone number already registered");
        }

        String otp = OtpUtil.generateOtp();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.CUSTOMER)
                .otp(otp)
                .otpExpiry(LocalDateTime.now().plusMinutes(5))
                .isVerified(false)
                .build();

        userRepository.save(user);

        //SEND EMAIL
        emailService.sendOtp(user.getEmail(), otp);

        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
    @Override
    public void verifyOtp(VerifyOtpRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getOtp().equals(request.getOtp())) {
            throw new BadRequestException("Invalid OTP");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expired");
        }

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);
    }
}