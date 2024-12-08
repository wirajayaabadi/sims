package com.example.sims.repository;

import com.example.sims.entity.BalanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface BalanceLogRepository extends JpaRepository<BalanceLog, BigInteger> {
}
