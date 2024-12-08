package com.example.sims.controller;

import com.example.sims.enums.StatusCode;
import com.example.sims.model.ApiResponse;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Void>> constraintViolationException(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.<Void>builder()
                    .status(StatusCode.BAD_REQUEST.getCode())
                    .message(e.getMessage())
                    .build());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<Void>> responseStatusException(ResponseStatusException e) {
    return ResponseEntity.status(e.getStatusCode())
            .body(ApiResponse.<Void>builder()
                    .status(StatusCode.UNAUTHORIZED_TOKEN.getCode())
                    .message(e.getReason())
                    .data(null)
                    .build());
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ApiResponse<Void>> jwtException(JwtException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.<Void>builder()
                    .status(StatusCode.UNAUTHORIZED_TOKEN.getCode())
                    .message(e.getMessage())
                    .data(null)
                    .build());
  }
}
