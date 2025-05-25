package com.devsu.common.domain;

import java.io.Serializable;

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
@Table(name = "persona")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPerson;
    
    @Column(name = "nombres")
    private String name;

    @Column(name = "direccion")   
    private String address;

    @Column(name = "telefono")
    private String phone;   

    @Column(name = "email") 
    private String email;
    
    @Column(name = "identificacion")
    private String identificationNumber;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_parametro")
    private Parameter idTypeIDNumber;

    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id_parametro")
    private Parameter idGender;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id_parametro")
    private Parameter idStatus;

}
