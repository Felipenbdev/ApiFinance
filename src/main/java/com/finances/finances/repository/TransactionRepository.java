package com.finances.finances.repository;

import com.finances.finances.model.Transaction;
import com.finances.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByUser(User user);
}
