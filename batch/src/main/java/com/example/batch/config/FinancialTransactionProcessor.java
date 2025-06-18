package com.example.batch.config;

import com.example.batch.model.Transaction;
import com.netflix.spectator.impl.AtomicDouble;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


/*@Component
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)*/

public class FinancialTransactionProcessor  {

 /*   private static final AtomicDouble totalDebit = new AtomicDouble(0);

    private static final AtomicDouble totalCredit = new AtomicDouble(0);
    public static double getTotalDebit() {
        return totalDebit.get();
    }

    public static double getTotalCredit() {
        return totalCredit.get();
    }


    @Override
    public Transaction process(Transaction transaction) {
        if ("D".equals(transaction.getTransactionType())) {
            totalDebit.addAndGet(transaction.getAmount());
        } else if ("W".equals(transaction.getTransactionType())) {
            totalCredit.addAndGet(transaction.getAmount());
        }
        return transaction;
    }*/
}
