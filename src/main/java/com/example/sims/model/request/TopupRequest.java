package com.example.sims.model.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopupRequest {
  @Min(value = 1, message = "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
  private BigInteger topUpAmount;
}
