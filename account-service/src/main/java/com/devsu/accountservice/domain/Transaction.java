package com.devsu.accountservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.devsu.common.domain.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private  Long idTransaction;    
    
    private BigDecimal amount;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_tipo_movimiento", referencedColumnName = "id_parametro")
    private Parameter idTypeTransaction;     

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_parametro")
    private Parameter idStatus;

}
