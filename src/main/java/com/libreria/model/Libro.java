package com.libreria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Book in the bookstore chain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LIBROS")
@NamedQuery(name = "Libro.findAll", query = "FROM Libro")
@NamedQuery(name = "Libro.findByTitulo", query = "FROM Libro l WHERE l.titulo = :titulo")
@NamedQuery(name = "Libro.findByAutorId", query = "FROM Libro l WHERE l.autor.id = :autorId")
@NamedQuery(name = "Libro.findByEditorialId", query = "FROM Libro l WHERE l.editorial.id = :editorialId")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editorial_id", nullable = false)
    private Editorial editorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToMany
    @JoinTable(
            name = "LIBRERIA_LIBRO",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "libreria_id")
    )
    private Set<Libreria> librerias = new HashSet<>();

    /**
     * Constructor for creating a book with basic info
     */
    public Libro(String titulo, BigDecimal precio) {
        this.titulo = titulo;
        this.precio = precio;
    }

    /**
     * Helper method for bidirectional relationships
     */
    public void addLibreria(Libreria libreria) {
        librerias.add(libreria);
        libreria.getLibros().add(this);
    }

    /**
     * Helper method for bidirectional relationships
     */
    public void removeLibreria(Libreria libreria) {
        librerias.remove(libreria);
        libreria.getLibros().remove(this);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", precio=" + precio +
                '}';
    }
}