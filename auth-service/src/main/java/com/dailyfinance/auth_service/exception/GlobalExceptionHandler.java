package com.dailyfinance.auth_service.exception;

import com.dailyfinance.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 409 - Already Exists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleConflict(ResourceAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), 409);
    }

    // 🔴 400 - Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException ex) {
        return buildResponse(ex.getMessage(), 400);
    }

    // 🔴 401 - Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(ex.getMessage(), 401);
    }

    // 🔴 404 - Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(ex.getMessage(), 404);
    }

    // 🔴 Validation Errors (DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        String error = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return buildResponse(error, 400);
    }

    // 🔴 Generic Exception (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobal(Exception ex) {
        return buildResponse("Internal Server Error", 500);
    }

    // 🔧 Common builder
    private ResponseEntity<ApiResponse<Object>> buildResponse(String message, int status) {
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .success(false)
                        .message(message)
                        .data(null)
                        .timestamp(Instant.now())
                        .statusCode(status)
                        .build()
        );
    }
}