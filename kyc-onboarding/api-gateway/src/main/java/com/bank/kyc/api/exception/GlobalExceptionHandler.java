package com.bank.kyc.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SimpleErrorResponse> handleAll(Exception ex) {
        SimpleErrorResponse error = new SimpleErrorResponse("INTERNAL_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

class SimpleErrorResponse {
    private String code;
    private String message;
    public SimpleErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}