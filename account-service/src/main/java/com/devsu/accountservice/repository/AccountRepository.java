package com.devsu.accountservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsu.accountservice.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    @Query(value = "SELECT * FROM cuenta WHERE id_cliente = :idCliente", nativeQuery = true)
    List<Account> findByCustomerId(@Param("idCliente") Long customerId);
    
    @Query(value = "SELECT * FROM cuenta WHERE numero_cuenta = :numeroCuenta", nativeQuery = true)
    Account findByAccountNumber(@Param("numeroCuenta") String accountNumber);
    
    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM cuenta c WHERE c.numero_cuenta = :numeroCuenta", nativeQuery = true)
    boolean existsByAccountNumber(@Param("numeroCuenta") String accountNumber);
} 