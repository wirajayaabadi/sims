package com.example.sims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
  private String invoiceNumber;
  private String transactionType;
  private String description;
  private Double totalAmount;
  private Timestamp createdOn;
}
