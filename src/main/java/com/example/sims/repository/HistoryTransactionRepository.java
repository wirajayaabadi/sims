package com.example.sims.repository;

import com.example.sims.dto.TransactionDTO;
import com.example.sims.entity.HistoryTransaction;
import com.example.sims.model.response.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface HistoryTransactionRepository extends JpaRepository<HistoryTransaction, BigInteger> {
  HistoryTransaction findTopByOrderByIdDesc();

  @Query(value = "SELECT hs.invoice_number, hs.transaction_type, hs.description, hs.total_amount, hs.created_on FROM history_transactions hs WHERE hs.user_id = :userId LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<Object[]> findHistoryTransactionsByUserIdWithLimitAndOffset(@Param("userId") BigInteger userId, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
