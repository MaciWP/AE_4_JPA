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
 * Entity class representing a Publisher in the bookstore chain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "EDITORIALES")
@NamedQuery(name = "Editorial.findAll", query = "FROM Editorial")
@NamedQuery(name = "Editorial.findByNombre", query = "FROM Editorial e WHERE e.nombre = :nombre")
public class Editorial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @OneToMany(mappedBy = "editorial", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Libro> librosPublicados = new ArrayList<>();

    /**
     * Constructor for creating a publisher with basic info
     */
    public Editorial(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    /**
     * Helper method to maintain bidirectional relationship
     */
    public void addLibro(Libro libro) {
        librosPublicados.add(libro);
        libro.setEditorial(this);
    }

    /**
     * Helper method to maintain bidirectional relationship
     */
    public void removeLibro(Libro libro) {
        librosPublicados.remove(libro);
        libro.setEditorial(null);
    }

    @Override
    public String toString() {
        return "Editorial{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}