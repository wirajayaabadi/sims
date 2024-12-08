package com.example.sims.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
  @NotBlank(message = "Email tidak boleh kosong")
  @Email(message = "Parameter email tidak sesuai format")
  private String email;

  @NotBlank(message = "Password tidak boleh kosong")
  private String password;
}
