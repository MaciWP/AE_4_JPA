package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Project in the company model.
 * Demonstrates a Many-to-Many relationship with Employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "PROYECTOS")
@NamedQuery(name = "Proyecto.findAll", query = "FROM Proyecto")
@NamedQuery(name = "Proyecto.findByPresupuestoRange",
        query = "FROM Proyecto p WHERE p.presupuesto BETWEEN :minPresupuesto AND :maxPresupuesto")
@NamedQuery(name = "Proyecto.findByFechaInicio",
        query = "FROM Proyecto p WHERE p.fechaInicio = :fechaInicio")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "presupuesto")
    private Double presupuesto;

    // Many-to-Many relationship with Empleado
    @ManyToMany(mappedBy = "proyectos")
    private Set<Empleado> empleados = new HashSet<>();

    /**
     * Constructor for creating a project with basic information
     */
    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Double presupuesto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
    }

    /**
     * Constructor for creating a project with ID (useful for references)
     */
    public Proyecto(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", presupuesto=" + presupuesto +
                '}';
    }
}