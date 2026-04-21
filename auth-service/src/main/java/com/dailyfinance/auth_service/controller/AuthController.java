package com.dailyfinance.auth_service.controller;

import com.dailyfinance.auth_service.dto.request.*;
import com.dailyfinance.auth_service.dto.response.*;
import com.dailyfinance.auth_service.service.AuthService;
import com.dailyfinance.auth_service.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseUtil.success(
                response,
                "User registered successfully",
                201
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseUtil.success(
                response,
                "Login successful",
                200
        );
    }

    // VERIFY TOKEN
    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<VerifyResponse>> verify(
            @RequestHeader("Authorization") String header) {

        String token = header.replace("Bearer ", "");

        VerifyResponse response = authService.verifyToken(token);

        return ResponseUtil.success(
                response,
                "Token is valid",
                200
        );
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Object>> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        authService.verifyOtp(request);

        return ResponseUtil.success(
                null,
                "OTP verified successfully",
                200
        );
    }
}