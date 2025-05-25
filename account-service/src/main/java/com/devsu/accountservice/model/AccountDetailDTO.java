package com.devsu.accountservice.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class AccountDetailDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private List<TransactionDetailDTO> transactions;
} 