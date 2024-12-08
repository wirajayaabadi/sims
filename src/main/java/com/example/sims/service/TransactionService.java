package com.example.sims.service;

import com.example.sims.dto.TransactionDTO;
import com.example.sims.entity.Balance;
import com.example.sims.entity.BalanceLog;
import com.example.sims.entity.HistoryTransaction;
import com.example.sims.entity.User;
import com.example.sims.model.request.TopupRequest;
import com.example.sims.model.request.TransactionRequest;
import com.example.sims.model.response.BalanceResponse;
import com.example.sims.model.response.HistoryTransactionResponse;
import com.example.sims.model.response.TransactionResponse;
import com.example.sims.repository.*;
import com.example.sims.util.InvoiceGenerator;
import com.example.sims.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

  @Autowired
  private BalanceRepository balanceRepository;

  @Autowired
  private BalanceLogRepository balanceLogRepository;

  @Autowired
  private HistoryTransactionRepository historyTransactionRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private InvoiceGenerator invoiceGenerator;
  @Autowired
  private ServiceRepository serviceRepository;

  public BalanceResponse getBalance(HttpServletRequest request) {
    String email = jwtUtil.getEmailFromToken(request);

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email tidak ditemukan"));

    Balance balance = balanceRepository.findByUserId(user.getId());

    return new BalanceResponse(balance.getBalance());
  }

  @Transactional
  public BalanceResponse topUpBalance(HttpServletRequest request, TopupRequest payload) {
    validationService.validate(payload);

    String email = jwtUtil.getEmailFromToken(request);
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email tidak ditemukan"));

    Balance balance = balanceRepository.findByUserId(user.getId());
    balance.setBalance(balance.getBalance() + payload.getTopUpAmount().doubleValue());
    balance = balanceRepository.save(balance);

    BalanceLog balanceLog = new BalanceLog();
    balanceLog.setBalanceId(balance.getId());
    balanceLog.setTransactionType("debit");
    balanceLog.setAmount(payload.getTopUpAmount().doubleValue());
    balanceLog.setDescription("Top up balance transaction");
    balanceLogRepository.save(balanceLog);

    HistoryTransaction history = historyTransactionRepository.findTopByOrderByIdDesc();

    String invoiceNumber = invoiceGenerator.generateInvoiceNumber(history);

    HistoryTransaction historyTransaction = new HistoryTransaction();
    historyTransaction.setUserId(user.getId());
    historyTransaction.setServiceCode("TOPUP");
    historyTransaction.setServiceName("Top up transaction");
    historyTransaction.setInvoiceNumber(invoiceNumber);
    historyTransaction.setTransactionType("TOPUP");
    historyTransaction.setDescription("Top up balance transaction");
    historyTransaction.setTotalAmount(payload.getTopUpAmount().doubleValue());
    historyTransaction.setStatus("completed");
    historyTransactionRepository.save(historyTransaction);

    return new BalanceResponse(balance.getBalance());
  }

  @Transactional
  public TransactionResponse createTransaction(HttpServletRequest request, TransactionRequest payload) {
    System.out.println(payload.getServiceCode());
    com.example.sims.entity.Service service = serviceRepository
            .findByServiceCode(payload.getServiceCode())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service atau layanan tidak ditemukan"));

    String email = jwtUtil.getEmailFromToken(request);
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email tidak ditemukan"));

    Balance balance = balanceRepository.findByUserId(user.getId());
    if(balance.getBalance() < service.getServiceTariff()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo anda tidak mencukupi.");
    }
    balance.setBalance(balance.getBalance() - service.getServiceTariff());
    balance = balanceRepository.save(balance);

    BalanceLog balanceLog = new BalanceLog();
    balanceLog.setBalanceId(balance.getId());
    balanceLog.setTransactionType("credit");
    balanceLog.setAmount(service.getServiceTariff());
    balanceLog.setDescription("Pembayaran layanan " + service.getServiceName());
    balanceLogRepository.save(balanceLog);

    HistoryTransaction history = historyTransactionRepository.findTopByOrderByIdDesc();
    String invoiceNumber = invoiceGenerator.generateInvoiceNumber(history);

    HistoryTransaction historyTransaction = new HistoryTransaction();
    historyTransaction.setUserId(user.getId());
    historyTransaction.setServiceCode(service.getServiceCode());
    historyTransaction.setServiceName(service.getServiceName());
    historyTransaction.setInvoiceNumber(invoiceNumber);
    historyTransaction.setTransactionType("PAYMENT");
    historyTransaction.setDescription("Pembayaran layanan " + service.getServiceName());
    historyTransaction.setTotalAmount(service.getServiceTariff());
    historyTransaction.setStatus("completed");
    historyTransaction = historyTransactionRepository.save(historyTransaction);

    return new TransactionResponse(historyTransaction.getInvoiceNumber(),
            historyTransaction.getServiceCode(),
            historyTransaction.getServiceName(),
            historyTransaction.getTransactionType(),
            historyTransaction.getTotalAmount(),
            historyTransaction.getCreatedOn()
    );
  }

  public HistoryTransactionResponse getHistoryTransactions(HttpServletRequest request, Integer offset, Integer limit) {
    offset = offset == null ? 0 : offset;
    limit = limit == null || limit <= 0 ? Integer.MAX_VALUE : limit;

    String email = jwtUtil.getEmailFromToken(request);
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email tidak ditemukan"));

    List<Object[]> historyTransaction = historyTransactionRepository.findHistoryTransactionsByUserIdWithLimitAndOffset(user.getId(), offset, limit);
    List<TransactionDTO> data = historyTransaction.stream()
            .map(result -> new TransactionDTO(
                    (String) result[0],
                    (String) result[1],
                    (String) result[2],
                    (Double) result[3],
                    (Timestamp) result[4]
            )).collect(Collectors.toList());

    return new HistoryTransactionResponse(offset, limit, data);
  }
}
