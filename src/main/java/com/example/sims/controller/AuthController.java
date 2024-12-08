package com.example.sims.controller;

import com.example.sims.enums.StatusCode;
import com.example.sims.model.ApiResponse;
import com.example.sims.model.request.LoginRequest;
import com.example.sims.model.response.TokenResponse;
import com.example.sims.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping(path = "/api/v1/login")
  public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
    TokenResponse token = authService.login(request);
    return ResponseEntity.ok(ApiResponse.<TokenResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Login Sukses")
            .data(token)
            .build());
  }

}
