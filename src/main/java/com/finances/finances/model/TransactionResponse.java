package com.finances.finances.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private TransactionType type;
}
