package com.uniquehire.loanagentmodule.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, HttpStatus status) {

        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(status.value())
                .build();

        return new ResponseEntity<>(response, status);
    }

}