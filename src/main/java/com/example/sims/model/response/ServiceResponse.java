package com.example.sims.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResponse {
  private String serviceCode;

  private String serviceName;

  private String serviceIcon;

  private Double serviceTariff;
}
