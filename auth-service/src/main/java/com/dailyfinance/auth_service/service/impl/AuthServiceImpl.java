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
    private final EmailService emailService;

    // ================= REGISTER =================
    @Override
    public RegisterResponse register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole() != null ? request.getRole() : Role.CUSTOMER)
                .agentArea(request.getAgentArea())
                .build();

        userRepository.save(user);

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

    // ================= LOGIN =================
    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        if (!user.isVerified()) {
            throw new UnauthorizedException("Please verify your account");
        }

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

    // ================= VERIFY TOKEN =================
    @Override
    public VerifyResponse verifyToken(String token) {

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

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

    // ================= CUSTOMER REGISTER (OTP) =================
    @Override
    public RegisterResponse registerCustomer(CustomerRegisterRequest request) {

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

    // ================= VERIFY OTP =================
    @Override
    public void verifyOtp(VerifyOtpRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
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