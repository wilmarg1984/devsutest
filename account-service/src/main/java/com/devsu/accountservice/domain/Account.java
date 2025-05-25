package com.devsu.accountservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.devsu.common.domain.Customer;
import com.devsu.common.domain.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuenta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idAccount;

    @Column(name = "numero_cuenta")
    private String accountNumber;

    @Column(name = "saldo_inicial")
    private BigDecimal initialBalance;

    @Column(name = "saldo_actual")
    private BigDecimal currentBalance;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_parametro")
    private Parameter accountType;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_parametro")
    private Parameter status;

    @PrePersist
    public void prePersist() {
        if (currentBalance == null) {
            currentBalance = initialBalance;
        }
    }
}
