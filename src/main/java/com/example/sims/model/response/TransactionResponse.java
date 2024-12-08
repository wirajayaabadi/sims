package com.example.sims.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
  private String invoiceNumber;
  private String serviceCode;
  private String serviceName;
  private String transactionType;
  private Double totalAmount;
  private Timestamp createdOn;
}
