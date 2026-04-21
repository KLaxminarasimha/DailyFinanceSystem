package com.example.customer.dto;

import com.example.customer.enums.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserTypeRequest {

    @NotNull(message = "User type is required")
    private UserType userType;
}