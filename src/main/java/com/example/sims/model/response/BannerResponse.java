package com.example.sims.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerResponse {
  private String bannerName;

  private String bannerImage;

  private String description;
}
