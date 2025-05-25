package com.devsu.accountservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDTO {
    
    private Long transactionId;
    
    private LocalDateTime date;
    
    @NotNull(message = "Transaction type is required")
    private Long transactionType;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "-999999999.99", message = "Amount must be greater than -999999999.99")
    @DecimalMax(value = "999999999.99", message = "Amount must be less than 999999999.99")
    private BigDecimal amount;
    
    @NotNull(message = "Account is required")
    private Long accountId;
    
    @NotNull(message = "Status is required")
    private Long idStatus;
} 