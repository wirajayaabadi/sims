package com.example.sims.controller;

import com.example.sims.dto.BannerDTO;
import com.example.sims.dto.ServiceDTO;
import com.example.sims.enums.StatusCode;
import com.example.sims.model.ApiResponse;
import com.example.sims.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InformationController {
  @Autowired
  private InformationService informationService;

  @GetMapping(path = "/api/v1/banner", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<BannerDTO>>> banner() {
    List<BannerDTO> banner = informationService.getBanners();
    return ResponseEntity.ok(ApiResponse.
            <List<BannerDTO>>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Sukses")
            .data(banner)
            .build());
  }

  @GetMapping(path = "/api/v1/service", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<List<ServiceDTO>>> service() {
    List<ServiceDTO> service = informationService.getService();
    return ResponseEntity.ok(ApiResponse.
            <List<ServiceDTO>>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Sukses")
            .data(service)
            .build());
  }
}
