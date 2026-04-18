package com.dailyfinance.auth_service.exception;
//400
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
