package com.libreria.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.libreria.dao.AutorDAO;
import com.libreria.dao.EditorialDAO;
import com.libreria.dao.LibreriaDAO;
import com.libreria.dao.LibroDAO;
import com.libreria.database.HibernateUtil;
import com.libreria.model.Autor;
import com.libreria.model.Editorial;
import com.libreria.model.Libreria;
import com.libreria.model.Libro;
import com.libreria.utils.Constants;

/**
 * Service that manages operations related to Requirement 1 (Bookstore Management System).
 * Contains the business logic for the bookstore system.
 */
public class BookstoreService {

    private final AutorDAO autorDAO;
    private final EditorialDAO editorialDAO;
    private final LibroDAO libroDAO;
    private final LibreriaDAO libreriaDAO;

    public BookstoreService() {
        this.autorDAO = new AutorDAO();
        this.editorialDAO = new EditorialDAO();
        this.libroDAO = new LibroDAO();
        this.libreriaDAO = new LibreriaDAO();
    }

    /**
     * Checks if data already exists in the database
     * @return true if data exists, false otherwise
     */
    private boolean existsData() {
        List<Autor> autores = autorDAO.findAll();
        return !autores.isEmpty();
    }

    /**
     * Creates sample data for the bookstore model,
     * only if no previous data exists.
     */
    public void createBookstoreData() {
        // Check if data already exists
        if (existsData()) {
            System.out.println(Constants.DATA_ALREADY_EXISTS);
            return;
        }

        try {
            // Create 3 authors
            Autor autor1 = new Autor(
                    Constants.AUTHOR_1_NAME,
                    Constants.AUTHOR_1_LASTNAME,
                    LocalDate.of(Constants.AUTHOR_1_BIRTH_YEAR, Constants.AUTHOR_1_BIRTH_MONTH, Constants.AUTHOR_1_BIRTH_DAY)
            );
            Autor autor2 = new Autor(
                    Constants.AUTHOR_2_NAME,
                    Constants.AUTHOR_2_LASTNAME,
                    LocalDate.of(Constants.AUTHOR_2_BIRTH_YEAR, Constants.AUTHOR_2_BIRTH_MONTH, Constants.AUTHOR_2_BIRTH_DAY)
            );
            Autor autor3 = new Autor(
                    Constants.AUTHOR_3_NAME,
                    Constants.AUTHOR_3_LASTNAME,
                    LocalDate.of(Constants.AUTHOR_3_BIRTH_YEAR, Constants.AUTHOR_3_BIRTH_MONTH, Constants.AUTHOR_3_BIRTH_DAY)
            );

            autor1 = autorDAO.save(autor1);
            autor2 = autorDAO.save(autor2);
            autor3 = autorDAO.save(autor3);

            // Create 2 publishers
            Editorial editorial1 = new Editorial(Constants.PUBLISHER_1_NAME, Constants.PUBLISHER_1_ADDRESS);
            Editorial editorial2 = new Editorial(Constants.PUBLISHER_2_NAME, Constants.PUBLISHER_2_ADDRESS);

            editorial1 = editorialDAO.save(editorial1);
            editorial2 = editorialDAO.save(editorial2);

            // Create 8 books
            Libro libro1 = new Libro(Constants.BOOK_1_TITLE, new BigDecimal(Constants.BOOK_1_PRICE));
            libro1.setAutor(autor1);
            libro1.setEditorial(editorial1);

            Libro libro2 = new Libro(Constants.BOOK_2_TITLE, new BigDecimal(Constants.BOOK_2_PRICE));
            libro2.setAutor(autor1);
            libro2.setEditorial(editorial1);

            Libro libro3 = new Libro(Constants.BOOK_3_TITLE, new BigDecimal(Constants.BOOK_3_PRICE));
            libro3.setAutor(autor2);
            libro3.setEditorial(editorial2);

            Libro libro4 = new Libro(Constants.BOOK_4_TITLE, new BigDecimal(Constants.BOOK_4_PRICE));
            libro4.setAutor(autor2);
            libro4.setEditorial(editorial2);

            Libro libro5 = new Libro(Constants.BOOK_5_TITLE, new BigDecimal(Constants.BOOK_5_PRICE));
            libro5.setAutor(autor3);
            libro5.setEditorial(editorial1);

            Libro libro6 = new Libro(Constants.BOOK_6_TITLE, new BigDecimal(Constants.BOOK_6_PRICE));
            libro6.setAutor(autor3);
            libro6.setEditorial(editorial1);

            Libro libro7 = new Libro(Constants.BOOK_7_TITLE, new BigDecimal(Constants.BOOK_7_PRICE));
            libro7.setAutor(autor1);
            libro7.setEditorial(editorial2);

            Libro libro8 = new Libro(Constants.BOOK_8_TITLE, new BigDecimal(Constants.BOOK_8_PRICE));
            libro8.setAutor(autor1);
            libro8.setEditorial(editorial2);

            libro1 = libroDAO.save(libro1);
            libro2 = libroDAO.save(libro2);
            libro3 = libroDAO.save(libro3);
            libro4 = libroDAO.save(libro4);
            libro5 = libroDAO.save(libro5);
            libro6 = libroDAO.save(libro6);
            libro7 = libroDAO.save(libro7);
            libro8 = libroDAO.save(libro8);

            // Create 2 bookstores
            Libreria libreria1 = new Libreria(Constants.BOOKSTORE_1_NAME, Constants.BOOKSTORE_1_OWNER, Constants.BOOKSTORE_1_ADDRESS);
            Libreria libreria2 = new Libreria(Constants.BOOKSTORE_2_NAME, Constants.BOOKSTORE_2_OWNER, Constants.BOOKSTORE_2_ADDRESS);

            libreria1 = libreriaDAO.save(libreria1);
            libreria2 = libreriaDAO.save(libreria2);


            libreriaDAO.addBook(libreria1.getId(), libro1.getId());
            libreriaDAO.addBook(libreria1.getId(), libro3.getId());
            libreriaDAO.addBook(libreria1.getId(), libro5.getId());
            libreriaDAO.addBook(libreria1.getId(), libro7.getId());

            libreriaDAO.addBook(libreria2.getId(), libro2.getId());
            libreriaDAO.addBook(libreria2.getId(), libro4.getId());
            libreriaDAO.addBook(libreria2.getId(), libro6.getId());
            libreriaDAO.addBook(libreria2.getId(), libro8.getId());

            System.out.println(Constants.SUCCESS_DATA_CREATED);
        } catch (Exception e) {
            System.err.println(String.format(Constants.ERROR_DATA_CREATION, e.getMessage()));
            e.printStackTrace();
        }
    }

    // Rest of the class remains unchanged

    /**
     * Deletes all data from the database related to the bookstore model
     */
    public void deleteAllData() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("DELETE FROM LIBRERIA_LIBRO", Void.class).executeUpdate();
            tx.commit();
            session.close();

            List<Libreria> librerias = libreriaDAO.findAll();
            for (Libreria libreria : librerias) {
                libreriaDAO.delete(libreria.getId());
            }

            List<Libro> libros = libroDAO.findAll();
            for (Libro libro : libros) {
                libroDAO.delete(libro.getId());
            }

            List<Editorial> editoriales = editorialDAO.findAll();
            for (Editorial editorial : editoriales) {
                editorialDAO.delete(editorial.getId());
            }

            List<Autor> autores = autorDAO.findAll();
            for (Autor autor : autores) {
                autorDAO.delete(autor.getId());
            }

            System.out.println(Constants.SUCCESS_DATA_DELETED);
        } catch (Exception e) {
            System.err.println(String.format(Constants.ERROR_DATA_DELETION, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Query 1: Shows all books with their publisher and author using DAO.
     */
    public void showAllBooksWithPublisherAndAuthor() {
        System.out.println(Constants.QUERY_TITLE_BOOKS_WITH_PUBLISHER_AUTHOR);

        List<Libro> libros = libroDAO.findAllWithPublisherAndAuthor();

        for (Libro libro : libros) {
            System.out.println(String.format(
                    Constants.DISPLAY_BOOK,
                    libro.getTitulo(),
                    libro.getPrecio(),
                    libro.getEditorial().getNombre(),
                    libro.getAutor().getNombre(),
                    libro.getAutor().getApellidos()
            ));
        }
    }

    /**
     * Query 2: Shows all authors with their associated books using DAO.
     */
    public void showAllAuthorsWithBooks() {
        System.out.println(Constants.QUERY_TITLE_AUTHORS_WITH_BOOKS);

        List<Autor> autores = autorDAO.findAllWithBooks();

        for (Autor autor : autores) {
            System.out.println(String.format(
                    Constants.DISPLAY_AUTHOR,
                    autor.getNombre(),
                    autor.getApellidos()
            ));

            if (autor.getLibros().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_BOOKS);
            } else {
                for (Libro libro : autor.getLibros()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_BOOK_DETAIL,
                            libro.getTitulo(),
                            libro.getPrecio()
                    ));
                }
            }
            System.out.println();
        }
    }

    /**
     * Query 3: Shows all bookstores with their associated books using DAO.
     */
    public void showAllBookstoresWithBooks() {
        System.out.println(Constants.QUERY_TITLE_BOOKSTORES_WITH_BOOKS);

        List<Libreria> librerias = libreriaDAO.findAllWithBooks();

        for (Libreria libreria : librerias) {
            System.out.println(String.format(
                    Constants.DISPLAY_BOOKSTORE,
                    libreria.getNombre(),
                    libreria.getNombreDueno(),
                    libreria.getDireccion()
            ));

            if (libreria.getLibros().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_INVENTORY);
            } else {
                for (Libro libro : libreria.getLibros()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_BOOK_IN_STORE,
                            libro.getTitulo()
                    ));
                }
            }
            System.out.println();
        }
    }

    /**
     * Query 4: Shows all books and the bookstores they are in using DAO.
     */
    public void showAllBooksWithBookstores() {
        System.out.println(Constants.QUERY_TITLE_BOOKS_WITH_BOOKSTORES);

        List<Libro> libros = libroDAO.findAllWithBookstores();

        for (Libro libro : libros) {
            System.out.println(String.format(
                    Constants.DISPLAY_BOOK_AVAILABILITY,
                    libro.getTitulo(),
                    libro.getPrecio()
            ));

            if (libro.getLibrerias().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_BOOKSTORE);
            } else {
                for (Libreria libreria : libro.getLibrerias()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_BOOKSTORE_NAME,
                            libreria.getNombre()
                    ));
                }
            }
            System.out.println();
        }
    }
}