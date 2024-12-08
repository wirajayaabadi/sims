package com.example.sims.repository;

import com.example.sims.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, BigInteger> {
  Balance findByUserId(BigInteger userId);
}
