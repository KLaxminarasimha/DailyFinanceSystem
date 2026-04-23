package com.uniquehire.TransactionAndReport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiWrapperDto<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private Integer statusCode;
}