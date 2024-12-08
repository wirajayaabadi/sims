package com.example.sims.model.response;

import com.example.sims.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryTransactionResponse {
  private Integer offset;
  private Integer limit;
  private List<TransactionDTO> records;
}
