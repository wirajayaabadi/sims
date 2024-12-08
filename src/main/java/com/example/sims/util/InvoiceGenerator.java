package com.example.sims.util;

import com.example.sims.entity.HistoryTransaction;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InvoiceGenerator {

  private static final AtomicInteger counter = new AtomicInteger(0);

  public String generateInvoiceNumber(HistoryTransaction history) {
    String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
    if(history == null) {
      counter.set(0);
    } else {
      String lastGenerated = new SimpleDateFormat("ddMMyyyy").format(history.getCreatedOn());
      if(!date.equals(lastGenerated)) {
        counter.set(0);
      } else {
        String[] parts = history.getInvoiceNumber().split("-");
        int counterSet = Integer.valueOf(parts[1]);
        counter.set(counterSet);
      }
    }

    int sequence = counter.incrementAndGet();
    String formattedSequence = String.format("%03d", sequence);

    return "INV" + date + "-" + formattedSequence;
  }
}
