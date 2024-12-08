package com.example.sims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BannerDTO {
  private String bannerName;
  private String bannerImage;
  private String description;
}
