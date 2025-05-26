package com.tapia.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionRestController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDetail.builder()
                        .message(errors.toString())
                        .build());
    }

    @ExceptionHandler(UserManagerException.class)
    public ResponseEntity<ExceptionDetail> handleUserManagerException(UserManagerException ex) {
        return ResponseEntity
                .status(ex.getExceptionDetail().getStatus())
                .body(ExceptionDetail.builder()
                        .message(ex.getExceptionDetail().getMessage())
                        .build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetail> handleUserManagerException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ExceptionDetail.builder()
                        .message(ex.getMessage())
                        .build());
    }

}
