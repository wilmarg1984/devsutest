package com.devsu.accountservice.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.accountservice.domain.Account;
import com.devsu.accountservice.domain.Transaction;
import com.devsu.accountservice.exception.InsufficientBalanceException;
import com.devsu.common.exceptions.BusinessException;
import com.devsu.common.exceptions.DataBaseException;
import com.devsu.accountservice.mapper.TransactionMapper;
import com.devsu.accountservice.model.TransactionDTO;
import com.devsu.accountservice.repository.AccountRepository;
import com.devsu.accountservice.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        try {
            // Validar y obtener la cuenta
            Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new BusinessException("Account not found with ID: " + transactionDTO.getAccountId()));

            // Validar el saldo si es un débito (monto negativo)
            BigDecimal currentBalance = account.getCurrentBalance();
            if (transactionDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal absAmount = transactionDTO.getAmount().abs();
                if (currentBalance.compareTo(absAmount) < 0) {
                    throw new InsufficientBalanceException(
                        String.format("Insufficient balance. Current balance: %s, Debit amount: %s",
                            currentBalance, absAmount));
                }
            }

            // Crear la transacción
            Transaction transaction = transactionMapper.toEntity(transactionDTO);
            transaction.setDate(new Date());
            transaction.setAccount(account);

            // Guardar la transacción y actualizar la cuenta
            accountRepository.save(account);
            transaction = transactionRepository.save(transaction);

            return transaction;

        } catch (InsufficientBalanceException e) {
            throw e;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error creating transaction", e);
        }
    }

    public BigDecimal getAccountBalance(Long accountId) {
        try {
            Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Account not found with ID: " + accountId));
            return account.getCurrentBalance();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error getting account balance", e);
        }
    }
} 