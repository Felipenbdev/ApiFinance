package com.finances.finances.controller;

import com.finances.finances.model.CreateTransactionRequest;
import com.finances.finances.model.TransactionResponse;
import com.finances.finances.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponse createTransaction(
            @RequestBody CreateTransactionRequest request
    ) {
        return transactionService.createTransaction(request);
    }
    
    @GetMapping
    public List<TransactionResponse> getUserTransactions() {
        return transactionService.getUserTransactions();
    }
}