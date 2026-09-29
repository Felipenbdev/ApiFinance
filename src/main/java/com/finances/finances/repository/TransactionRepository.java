package com.finances.finances.repository;

import com.finances.finances.model.Transaction;
import com.finances.finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByUser(User user);
    Optional<Transaction> findByIdAndUser(Long id, User user);
}
