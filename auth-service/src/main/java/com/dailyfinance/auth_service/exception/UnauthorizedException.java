package com.dailyfinance.auth_service.exception;
//401
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
