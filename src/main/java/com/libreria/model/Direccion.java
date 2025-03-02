package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entity class representing an Address in the company model.
 * Demonstrates a One-to-One relationship with Employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "DIRECCIONES")
@NamedQuery(name = "Direccion.findAll", query = "FROM Direccion")
@NamedQuery(name = "Direccion.findByCiudad", query = "FROM Direccion d WHERE d.ciudad = :ciudad")
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calle", nullable = false)
    private String calle;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "pais")
    private String pais;

    @OneToOne(mappedBy = "direccion")
    private Empleado empleado;

    /**
     * Constructor for creating an address with basic info
     */
    public Direccion(String calle, String ciudad, String codigoPostal, String pais) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}