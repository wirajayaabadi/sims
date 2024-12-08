package com.example.sims.repository;

import com.example.sims.dto.BannerDTO;
import com.example.sims.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, BigInteger> {
  @Query("SELECT new com.example.sims.dto.BannerDTO(b.bannerName, b.bannerImage, b.description) FROM Banner b")
  List<BannerDTO> findAllBanners();
}
