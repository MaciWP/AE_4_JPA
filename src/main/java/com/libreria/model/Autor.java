package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing an Author in the bookstore chain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "AUTORES")
@NamedQuery(name = "Autor.findAll", query = "FROM Autor")
@NamedQuery(name = "Autor.findByNombre", query = "FROM Autor a WHERE a.nombre = :nombre")
@NamedQuery(name = "Autor.findByApellidos", query = "FROM Autor a WHERE a.apellidos = :apellidos")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 200)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "autor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Libro> libros = new ArrayList<>();

    /**
     * Constructor for creating an author with basic info
     */
    public Autor(String nombre, String apellidos, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Helper method to maintain bidirectional relationship
     */
    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    /**
     * Helper method to maintain bidirectional relationship
     */
    public void removeLibro(Libro libro) {
        libros.remove(libro);
        libro.setAutor(null);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}