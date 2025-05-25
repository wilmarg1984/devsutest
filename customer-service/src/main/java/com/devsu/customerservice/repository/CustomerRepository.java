package com.devsu.customerservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import com.devsu.common.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Cosulta general de clientes
     * @return
     */
    @Query(value = "SELECT * FROM cliente", nativeQuery = true)
    public List<Customer> getCustomers();

}
