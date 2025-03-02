package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Libro;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Libro entity
 */
public class LibroDAO {

    private Session session;

    /**
     * Save a book to the database
     *
     * @param libro the book to save
     * @return the saved book with generated ID
     */
    public Libro save(Libro libro) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(libro);
            if (!existingTransaction) {
                tx.commit();
            }
            return libro;
        } catch (Exception e) {
            if (!existingTransaction && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Find a book by ID
     *
     * @param id the book ID
     * @return the found book or null if not found
     */
    public Libro findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libro libro = session.get(Libro.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return libro;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all books
     *
     * @return list of all books
     */
    public List<Libro> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Libro> query = session.createNamedQuery("Libro.findAll", Libro.class);
            List<Libro> result = query.getResultList();
            if (!existingTransaction) {
                tx.commit();
            }
            return result;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Find all books with their publisher and author
     *
     * @return list of books with publisher and author
     */
    public List<Libro> findAllWithPublisherAndAuthor() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Libro> query = session.createQuery(
                    "SELECT DISTINCT l FROM Libro l " +
                            "JOIN FETCH l.editorial " +
                            "JOIN FETCH l.autor",
                    Libro.class
            );
            List<Libro> result = query.getResultList();
            if (!existingTransaction) {
                tx.commit();
            }
            return result;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Find all books with their bookstores
     *
     * @return list of books with their bookstores
     */
    public List<Libro> findAllWithBookstores() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Libro> query = session.createQuery(
                    "SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.librerias",
                    Libro.class
            );
            List<Libro> result = query.getResultList();
            if (!existingTransaction) {
                tx.commit();
            }
            return result;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Update a book
     *
     * @param libro the book to update
     * @return the updated book
     */
    public Libro update(Libro libro) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libro updatedLibro = session.merge(libro);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedLibro;
        } catch (Exception e) {
            if (!existingTransaction && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Delete a book by ID
     *
     * @param id the book ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libro libro = session.get(Libro.class, id);
            if (libro != null) {
                session.remove(libro);
            }
            if (!existingTransaction) {
                tx.commit();
            }
        } catch (Exception e) {
            if (!existingTransaction && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }
}