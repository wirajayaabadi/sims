package com.example.sims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_transactions")
public class HistoryTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private BigInteger userId;

  private String serviceCode;

  private String serviceName;

  private String invoiceNumber;

  private String transactionType;

  private String description;

  private Double totalAmount;

  private String status;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdOn;
}
