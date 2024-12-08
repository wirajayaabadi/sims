package com.example.sims.controller;

import com.example.sims.enums.StatusCode;
import com.example.sims.model.ApiResponse;
import com.example.sims.model.request.TopupRequest;
import com.example.sims.model.request.TransactionRequest;
import com.example.sims.model.response.BalanceResponse;
import com.example.sims.model.response.HistoryTransactionResponse;
import com.example.sims.model.response.TransactionResponse;
import com.example.sims.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @GetMapping(path = "/api/v1/balance", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(HttpServletRequest request) {
    BalanceResponse balance = transactionService.getBalance(request);

    return ResponseEntity.ok(ApiResponse.<BalanceResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Get balance berhasil")
            .data(balance)
            .build());
  }

  @PostMapping(path = "/api/v1/topup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<BalanceResponse>> topUp(HttpServletRequest request, @RequestBody TopupRequest payload) {
    BalanceResponse balance = transactionService.topUpBalance(request, payload);

    return ResponseEntity.ok(ApiResponse.<BalanceResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Top Up Balance berhasil")
            .data(balance)
            .build());
  }

  @PostMapping(path = "/api/v1/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<TransactionResponse>> transaction(HttpServletRequest request, @RequestBody TransactionRequest payload) {
    TransactionResponse transaction = transactionService.createTransaction(request, payload);

    return ResponseEntity.ok(ApiResponse.<TransactionResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Transaksi berhasil")
            .data(transaction)
            .build());
  }

  @GetMapping(path = "/api/v1/transaction/history", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<HistoryTransactionResponse>> historyTransaction(HttpServletRequest request, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
    HistoryTransactionResponse history = transactionService.getHistoryTransactions(request, offset, limit);

    return ResponseEntity.ok(ApiResponse.<HistoryTransactionResponse>builder()
            .status(StatusCode.SUCCESS.getCode())
            .message("Get History Berhasil")
            .data(history)
            .build());
  }
}
