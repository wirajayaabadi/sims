package com.example.sims.repository;

import com.example.sims.dto.ServiceDTO;
import com.example.sims.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, BigInteger> {
  @Query("SELECT new com.example.sims.dto.ServiceDTO(s.serviceCode, s.serviceName, s.serviceIcon, s.serviceTariff) FROM Service s")
  List<ServiceDTO> findAllServices();

  Optional<Service> findByServiceCode(String serviceCode);
}
