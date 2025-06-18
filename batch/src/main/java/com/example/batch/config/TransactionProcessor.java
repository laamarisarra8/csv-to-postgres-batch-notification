package com.example.batch.config;

import com.example.batch.model.Transaction;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    //private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Transaction process(Transaction transaction) throws Exception {
       /* if (transaction.getTransaction_date() instanceof String) {
            transaction.setTransaction_date(
                    LocalDateTime.parse((String) transaction.getTransaction_date(), formatter)
            );
        }*/
        return transaction;
    }
}
