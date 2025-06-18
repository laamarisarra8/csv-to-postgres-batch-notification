package com.example.batch.repository;

import com.example.batch.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
