package com.devsu.accountservice.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsu.accountservice.model.AccountStatementDTO;

import com.devsu.accountservice.service.AccountStatementService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {


    private final AccountStatementService accountStatementService;

    @GetMapping("/reporte")
    public ResponseEntity<AccountStatementDTO> getAccountStatement(
            @RequestParam Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        AccountStatementDTO statement = accountStatementService.generateStatement(
            customerId, startDate, endDate);
        return ResponseEntity.ok(statement);
    }
    
}
