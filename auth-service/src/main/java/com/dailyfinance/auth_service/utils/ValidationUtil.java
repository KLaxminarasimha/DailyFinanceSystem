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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateRegister(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ResourceAlreadyExistsException("Phone number already registered");
        }

        if (request.getRole() == Role.AGENT && request.getAgentArea() == null) {
            throw new BadRequestException("Agent area is required");
        }
    }
    public void validateLogin(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

    }
    public void validatePassword(String password) {

        if (password.length() < 8 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[^a-zA-Z0-9].*")) {

            throw new BadRequestException(
                    "Password must contain uppercase, number, and special character"
            );
        }
    }
}