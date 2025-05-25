package com.devsu.accountservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.accountservice.domain.Account;
import com.devsu.accountservice.domain.Transaction;
import com.devsu.accountservice.model.AccountStatementDTO;
import com.devsu.accountservice.model.AccountDetailDTO;
import com.devsu.accountservice.model.TransactionDetailDTO;
import com.devsu.common.exceptions.BusinessException;
import com.devsu.common.exceptions.DataBaseException;
import com.devsu.accountservice.repository.AccountRepository;
import com.devsu.accountservice.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountStatementService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public AccountStatementDTO generateStatement(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            // Validar que existan cuentas para el cliente
            List<Account> accounts = accountRepository.findByCustomerId(customerId);
            if (accounts.isEmpty()) {
                throw new BusinessException("No accounts found for customer: " + customerId);
            }

            // Obtener todas las transacciones del período
            List<Transaction> transactions = transactionRepository.findTransactionsForStatement(
                customerId, startDate, endDate);

            // Agrupar transacciones por cuenta
            Map<Long, List<Transaction>> transactionsByAccount = transactions.stream()
                .collect(Collectors.groupingBy(t -> Long.valueOf(t.getAccount().getIdAccount())));

            // Construir el reporte
            AccountStatementDTO statement = new AccountStatementDTO();
            statement.setCustomerId(customerId);
            statement.setCustomerName(accounts.get(0).getCustomer().getIdPerson().getName());
            statement.setStartDate(startDate);
            statement.setEndDate(endDate);

            // Construir detalles de cuenta
            List<AccountDetailDTO> accountDetails = accounts.stream()
                .map(account -> {
                    AccountDetailDTO detail = new AccountDetailDTO();
                    detail.setAccountNumber(account.getAccountNumber());
                    detail.setAccountType(account.getAccountType().getName());
                    detail.setInitialBalance(account.getInitialBalance());
                    detail.setCurrentBalance(account.getCurrentBalance());

                    // Agregar transacciones de la cuenta
                    List<Transaction> accountTransactions = transactionsByAccount
                        .getOrDefault(account.getIdAccount(), List.of());
                    detail.setTransactions(mapTransactions(accountTransactions));

                    return detail;
                })
                .collect(Collectors.toList());

            statement.setAccounts(accountDetails);
            return statement;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error generating account statement", e);
        }
    }

    private List<TransactionDetailDTO> mapTransactions(List<Transaction> transactions) {
        return transactions.stream()
            .map(transaction -> {
                TransactionDetailDTO detail = new TransactionDetailDTO();
                detail.setDate(transaction.getDate());
                detail.setTransactionType(transaction.getIdTypeTransaction().getName());
                detail.setAmount(transaction.getAmount());
                return detail;
            })
            .collect(Collectors.toList());
    }
} 