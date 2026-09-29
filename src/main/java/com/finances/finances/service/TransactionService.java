package com.finances.finances.service;

import com.finances.finances.model.CreateTransactionRequest;
import com.finances.finances.model.Transaction;
import com.finances.finances.model.TransactionResponse;
import com.finances.finances.model.UpdateTransactionRequest;
import com.finances.finances.model.User;
import com.finances.finances.repository.TransactionRepository;
import com.finances.finances.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public TransactionResponse createTransaction(
            CreateTransactionRequest request
    ) {

        User user = getAuthenticatedUser();

        Transaction transaction = new Transaction();

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setType(request.getType());
        transaction.setUser(user);

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    public List<TransactionResponse> getUserTransactions() {

        User user = getAuthenticatedUser();

        List<Transaction> transactions =
                transactionRepository.findAllByUser(user);

        return transactions.stream()
                .map(this::toResponse)
                .toList();
    }

    public TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request
    ) {

        User user = getAuthenticatedUser();

        Transaction transaction =
                transactionRepository.findByIdAndUser(id, user)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction not found"
                        ));

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setType(request.getType());

        Transaction updatedTransaction =
                transactionRepository.save(transaction);

        return toResponse(updatedTransaction);
    }

    public void deleteTransaction(Long id) {

        User user = getAuthenticatedUser();

        Transaction transaction =
                transactionRepository.findByIdAndUser(id, user)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction not found"
                        ));

        transactionRepository.delete(transaction);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User not authenticated"
            );
        }

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }

    private TransactionResponse toResponse(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setDescription(transaction.getDescription());
        response.setDate(transaction.getDate());
        response.setType(transaction.getType());

        return response;
    }
}