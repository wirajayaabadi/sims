package com.example.sims.util;

import com.example.sims.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Autowired
  private JwtService jwtService;

  public String getEmailFromToken(@NonNull HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractUsername(jwt);

    return userEmail;
  }
}
