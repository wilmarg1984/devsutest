package com.devsu.accountservice.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class AccountStatementDTO {
    private Long customerId;
    private String customerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<AccountDetailDTO> accounts;
}