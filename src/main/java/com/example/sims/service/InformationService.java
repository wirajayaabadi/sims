package com.example.sims.service;

import com.example.sims.dto.BannerDTO;
import com.example.sims.dto.ServiceDTO;
import com.example.sims.repository.BannerRepository;
import com.example.sims.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService {

  @Autowired
  private BannerRepository bannerRepository;

  @Autowired
  private ServiceRepository serviceRepository;

  public List<BannerDTO> getBanners() {
    List<BannerDTO> banner = bannerRepository.findAllBanners();
    return banner;
  }

  public List<ServiceDTO> getService() {
    List<ServiceDTO> service = serviceRepository.findAllServices();
    return service;
  }
}
