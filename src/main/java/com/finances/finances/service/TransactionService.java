package com.finances.finances.service;

import com.finances.finances.model.*;
import com.finances.finances.repository.TransactionRepository;
import com.finances.finances.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setType(request.getType());
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse();

        response.setId(savedTransaction.getId());
        response.setAmount(savedTransaction.getAmount());
        response.setDescription(savedTransaction.getDescription());
        response.setDate(savedTransaction.getDate());
        response.setType(savedTransaction.getType());

        return response;
    }
    public List<TransactionResponse> getUserTransactions() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> transactions =
                transactionRepository.findAllByUser(user);

        return transactions.stream()
                .map(transaction -> {
                    TransactionResponse response = new TransactionResponse();

                    response.setId(transaction.getId());
                    response.setAmount(transaction.getAmount());
                    response.setDescription(transaction.getDescription());
                    response.setDate(transaction.getDate());
                    response.setType(transaction.getType());

                    return response;
                })
                .toList();
    }

    public void deleteTransaction(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transactionRepository.delete(transaction);
    }
    public TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = transactionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setType(request.getType());

        Transaction updatedTransaction =
                transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse();

        response.setId(updatedTransaction.getId());
        response.setAmount(updatedTransaction.getAmount());
        response.setDescription(updatedTransaction.getDescription());
        response.setDate(updatedTransaction.getDate());
        response.setType(updatedTransaction.getType());

        return response;
    }

}
