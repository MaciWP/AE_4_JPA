package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Libreria;

import com.libreria.model.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Libreria entity
 */
public class LibreriaDAO {

    private Session session;

    /**
     * Save a bookstore to the database
     *
     * @param libreria the bookstore to save
     * @return the saved bookstore with generated ID
     */
    public Libreria save(Libreria libreria) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(libreria);
            if (!existingTransaction) {
                tx.commit();
            }
            return libreria;
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
     * Find a bookstore by ID
     *
     * @param id the bookstore ID
     * @return the found bookstore or null if not found
     */
    public Libreria findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libreria libreria = session.get(Libreria.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return libreria;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all bookstores
     *
     * @return list of all bookstores
     */
    public List<Libreria> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Libreria> query = session.createNamedQuery("Libreria.findAll", Libreria.class);
            List<Libreria> result = query.getResultList();
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
     * Find all bookstores with their books
     *
     * @return list of bookstores with their books
     */
    public List<Libreria> findAllWithBooks() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Libreria> query = session.createQuery(
                    "SELECT DISTINCT l FROM Libreria l LEFT JOIN FETCH l.libros",
                    Libreria.class
            );
            List<Libreria> result = query.getResultList();
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
     * Update a bookstore
     *
     * @param libreria the bookstore to update
     * @return the updated bookstore
     */
    public Libreria update(Libreria libreria) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libreria updatedLibreria = session.merge(libreria);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedLibreria;
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
     * Delete a bookstore by ID
     *
     * @param id the bookstore ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Libreria libreria = session.get(Libreria.class, id);
            if (libreria != null) {
                session.remove(libreria);
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

    /**
     * Add a book to a bookstore - FIXED VERSION
     * This method properly manages both sides of the relationship and ensures
     * that changes are properly persisted to the database.
     *
     * @param libreriaId the bookstore ID
     * @param libroId the book ID to add
     * @return updated Libreria instance
     */
    public Libreria addBook(Long libreriaId, Long libroId) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            // Get fresh instances from this session
            Libreria libreria = session.get(Libreria.class, libreriaId);
            Libro libro = session.get(Libro.class, libroId);

            if (libreria != null && libro != null) {
                // Use helper method to maintain bidirectional relationship
                libreria.addLibro(libro);

                // Explicitly merge the entities back to the session
                session.merge(libreria);
                session.merge(libro);

                // Flush to ensure SQL is sent to the database
                session.flush();
            }

            if (!existingTransaction) {
                tx.commit();
            }

            return libreria;
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