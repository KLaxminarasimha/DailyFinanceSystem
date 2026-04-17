package com.dailyfinance.auth_service.utils;

import com.dailyfinance.auth_service.dto.request.LoginRequest;
import com.dailyfinance.auth_service.dto.request.RegisterRequest;
import com.dailyfinance.auth_service.entity.Role;
import com.dailyfinance.auth_service.entity.User;
import com.dailyfinance.auth_service.exception.BadRequestException;
import com.dailyfinance.auth_service.exception.ResourceAlreadyExistsException;
import com.dailyfinance.auth_service.exception.ResourceNotFoundException;
import com.dailyfinance.auth_service.exception.UnauthorizedException;
import com.dailyfinance.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
}