package com.dailyfinance.auth_service.controller;

import com.dailyfinance.auth_service.dto.request.CustomerRegisterRequest;
import com.dailyfinance.auth_service.dto.response.ApiResponse;
import com.dailyfinance.auth_service.dto.response.RegisterResponse;
import com.dailyfinance.auth_service.service.AuthService;
import com.dailyfinance.auth_service.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerAuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerCustomer(
            @RequestBody CustomerRegisterRequest request) {

        return ResponseUtil.success(
                authService.registerCustomer(request),
                "Customer registered successfully",
                201
        );
    }
}
