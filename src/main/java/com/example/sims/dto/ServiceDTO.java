package com.example.sims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceDTO {
  private String serviceCode;
  private String serviceName;
  private String serviceIcon;
  private Double serviceTariff;
}
