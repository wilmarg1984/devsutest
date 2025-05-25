package com.devsu.accountservice.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionDetailDTO {
    private Date date;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private String description;
} 