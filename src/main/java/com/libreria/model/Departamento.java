package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Department in the company model.
 * Demonstrates a One-to-Many relationship with Employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "DEPARTAMENTOS")
@NamedQuery(name = "Departamento.findAll", query = "FROM Departamento")
@NamedQuery(name = "Departamento.findByNombre", query = "FROM Departamento d WHERE d.nombre = :nombre")
@NamedQuery(name = "Departamento.findByUbicacion", query = "FROM Departamento d WHERE d.ubicacion = :ubicacion")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ubicacion")
    private String ubicacion;

    // One-to-Many relationship with Empleado
    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados = new ArrayList<>();

    /**
     * Constructor for creating a department with basic info
     */
    public Departamento(String nombre, String descripcion, String ubicacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}