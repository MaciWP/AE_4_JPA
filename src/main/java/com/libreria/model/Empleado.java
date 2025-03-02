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
 * Entity class representing an Employee in the company model.
 * Demonstrates all three types of relationships:
 * - One-to-One with Address
 * - Many-to-One with Department
 * - Many-to-Many with Project
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "EMPLEADOS")
@NamedQuery(name = "Empleado.findAll", query = "FROM Empleado")
@NamedQuery(name = "Empleado.findByNombre", query = "FROM Empleado e WHERE e.nombre = :nombre")
@NamedQuery(name = "Empleado.findByDepartamentoId", query = "FROM Empleado e WHERE e.departamento.id = :departamentoId")
@NamedQuery(name = "Empleado.updateSalario", query = "UPDATE Empleado e SET e.salario = :salario WHERE e.id = :id")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @Column(name = "salario")
    private Double salario;

    // One-to-One relationship with Direccion
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", unique = true)
    private Direccion direccion;

    // Many-to-One relationship with Departamento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    // Many-to-Many relationship with Proyecto
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "EMPLEADO_PROYECTO",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();

    /**
     * Constructor for creating an employee with basic info
     */
    public Empleado(String nombre, String apellido, String email, LocalDate fechaContratacion, Double salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
    }

    /**
     * Helper method for managing relationships
     */
    public void asignarDireccion(Direccion direccion) {
        this.direccion = direccion;
        direccion.setEmpleado(this);
    }

    /**
     * Helper method for managing relationships
     */
    public void asignarDepartamento(Departamento departamento) {
        this.departamento = departamento;
        departamento.getEmpleados().add(this);
    }

    /**
     * Helper method for managing relationships
     */
    public void dejarDepartamento() {
        if (this.departamento != null) {
            this.departamento.getEmpleados().remove(this);
            this.departamento = null;
        }
    }

    /**
     * Helper method for managing relationships
     */
    public void asignarProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.getEmpleados().add(this);
    }

    /**
     * Helper method for managing relationships
     */
    public void dejarProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.getEmpleados().remove(this);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", fechaContratacion=" + fechaContratacion +
                ", salario=" + salario +
                '}';
    }
}