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
@Table(name = "parametro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro")
    private  Long idParameter;

    @Column(name = "nombre")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_tipo_parametro", referencedColumnName = "id_tipo_parametro")
    private TypeParameter idTypeParameter;
    

}
