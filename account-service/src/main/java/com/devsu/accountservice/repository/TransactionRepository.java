package com.devsu.accountservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsu.accountservice.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query(value = "SELECT * FROM movimiento WHERE id_cuenta = :idCuenta ORDER BY fecha DESC", nativeQuery = true)
    List<Transaction> findByAccountId(@Param("idCuenta") Long accountId);
    
    @Query(value = "SELECT * FROM movimiento WHERE id_cuenta = :idCuenta AND fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha DESC", nativeQuery = true)
    List<Transaction> findByAccountIdAndDateRange(
        @Param("idCuenta") Long accountId,
        @Param("fechaInicio") LocalDateTime startDate,
        @Param("fechaFin") LocalDateTime endDate
    );
    
    @Query(value = "SELECT * FROM movimiento WHERE fecha BETWEEN :fechaInicio AND :fechaFin AND id_cuenta IN (SELECT id_cuenta FROM cuenta WHERE id_cliente = :idCliente) ORDER BY fecha DESC", nativeQuery = true)
    List<Transaction> findByCustomerIdAndDateRange(
        @Param("idCliente") Long customerId,
        @Param("fechaInicio") LocalDateTime startDate,
        @Param("fechaFin") LocalDateTime endDate
    );

    @Query(value = """
        SELECT t.* FROM transaction t 
        INNER JOIN account a ON t.account_id = a.account_id 
        WHERE a.customer_id = :customerId 
        AND t.date BETWEEN :startDate AND :endDate 
        ORDER BY a.account_id, t.date
        """, nativeQuery = true)
    List<Transaction> findTransactionsForStatement(
        @Param("customerId") Long customerId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
} 