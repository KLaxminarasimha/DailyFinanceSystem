package com.uniquehire.TransactionAndReport.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    @NotNull
    private Boolean success;

    @NotBlank
    private String message;

    @NotNull
    private LocalDateTime timestamp;

    private T data;

    @NotNull
    private Integer statusCode;
}
