package com.devsu.msaccount.repository;

import com.devsu.msaccount.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
