package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.exceptions.EmailAlreadyTaken;
import com.darmokhval.Backend_part.exceptions.UsernameAlreadyTaken;
import com.darmokhval.Backend_part.models.dto.Authentication.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomErrorHandler {
    @ExceptionHandler({UsernameAlreadyTaken.class})
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyTakenException(UsernameAlreadyTaken exception) {
        String details = exception.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }
    @ExceptionHandler({EmailAlreadyTaken.class})
    public ResponseEntity<ErrorDTO> handleEmailAlreadyTakenException(EmailAlreadyTaken exception) {
        String details = exception.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }
}
