package com.uniquehire.TransactionAndReport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    @NotNull
    private Boolean success;

    @NotBlank
    private String message;

    @NotBlank
    private String errorCode;

    private Object details;

    @NotNull
    private Integer statusCode;

    @NotNull
    private LocalDateTime timestamp;
}
