package com.securityuserapp.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ApiError {

    private final LocalDateTime timestamp;
    private final HttpStatus status;
    private final String error;
    private final String message;
    private final Map<String, String> validationErrors;

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = null;
        this.message = message;
        this.validationErrors = null;
    }

    public ApiError(
            HttpStatus status,
            String error,
            String message,
            Map<String, String> validationErrors) {

        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
    }
}