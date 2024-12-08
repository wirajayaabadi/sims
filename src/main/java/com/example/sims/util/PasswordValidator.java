package com.example.sims.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
  public static boolean isValidPassword(String password) {
    if (password == null || password.length() < 8) {
      return false;
    }
    boolean hasNumber = password.matches(".*\\d.*");

    return hasNumber;
  }
}
