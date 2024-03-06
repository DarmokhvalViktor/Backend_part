package com.darmokhval.Backend_part.controller;

import com.darmokhval.Backend_part.exception.*;
import com.darmokhval.Backend_part.model.dto.Authentication.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomErrorHandler {
    @ExceptionHandler({UsernameAlreadyTaken.class})
    public ResponseEntity<ErrorDTO> handleUsernameAlreadyTakenException(UsernameAlreadyTaken exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({EmailAlreadyTaken.class})
    public ResponseEntity<ErrorDTO> handleEmailAlreadyTakenException(EmailAlreadyTaken exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({InvalidRefreshTokenSignature.class})
    public ResponseEntity<ErrorDTO> handleInvalidRefreshTokenSignature(InvalidRefreshTokenSignature exception) {
        return createResponse(exception);
    }

    @ExceptionHandler({WorksheetNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleWorksheetNotFoundException(WorksheetNotFoundException exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({RefreshTokenHasExpired.class})
    public ResponseEntity<ErrorDTO> handleRefreshTokenHasExpiredException(RefreshTokenHasExpired exception) {
        return createResponse(exception);
    }
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptionException(UserNotFoundException exception) {
        return createResponse(exception);
    }

    private ResponseEntity<ErrorDTO> createResponse(Exception exception) {
        String details = exception.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }

}
