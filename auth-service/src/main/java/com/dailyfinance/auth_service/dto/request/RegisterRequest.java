package com.dailyfinance.auth_service.dto.request;

import com.dailyfinance.auth_service.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Email(message="Invalid email format")
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=8,message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Role is required")
    private Role role;

    private String agentArea;

}
