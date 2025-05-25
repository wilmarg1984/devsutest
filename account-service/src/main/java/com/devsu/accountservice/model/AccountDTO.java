package com.devsu.accountservice.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountDTO {
    
    private Long accountId;
    
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Initial balance is required")
    @Positive(message = "Initial balance must be greater than zero")
    private BigDecimal initialBalance;
    
    private BigDecimal currentBalance;
    @NotNull(message = "Account type is required")
    private Long accountType;
    
    private Long status;
} 