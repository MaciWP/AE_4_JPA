package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Bookstore in the bookstore chain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LIBRERIAS")
@NamedQuery(name = "Libreria.findAll", query = "FROM Libreria")
@NamedQuery(name = "Libreria.findByNombre", query = "FROM Libreria l WHERE l.nombre = :nombre")
@NamedQuery(name = "Libreria.findByDireccion", query = "FROM Libreria l WHERE l.direccion = :direccion")
public class Libreria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "nombre_dueno", length = 100)
    private String nombreDueno;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @ManyToMany(mappedBy = "librerias")
    private Set<Libro> libros = new HashSet<>();

    /**
     * Constructor for creating a bookstore with basic info
     */
    public Libreria(String nombre, String nombreDueno, String direccion) {
        this.nombre = nombre;
        this.nombreDueno = nombreDueno;
        this.direccion = direccion;
    }

    /**
     * Helper method for bidirectional relationships
     */
    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.getLibrerias().add(this);
    }

    /**
     * Helper method for bidirectional relationships
     */
    public void removeLibro(Libro libro) {
        libros.remove(libro);
        libro.getLibrerias().remove(this);
    }

    @Override
    public String toString() {
        return "Libreria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nombreDueno='" + nombreDueno + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}