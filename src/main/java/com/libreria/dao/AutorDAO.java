package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Autor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Autor entity
 */
public class AutorDAO {

    private Session session;

    /**
     * Save an author to the database
     *
     * @param autor the author to save
     * @return the saved author with generated ID
     */
    public Autor save(Autor autor) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(autor);
            if (!existingTransaction) {
                tx.commit();
            }
            return autor;
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
     * Find an author by ID
     *
     * @param id the author ID
     * @return the found author or null if not found
     */
    public Autor findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Autor autor = session.get(Autor.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return autor;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all authors
     *
     * @return list of all authors
     */
    public List<Autor> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Autor> query = session.createNamedQuery("Autor.findAll", Autor.class);
            List<Autor> result = query.getResultList();
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
     * Find all authors with their associated books
     *
     * @return list of authors with their books
     */
    public List<Autor> findAllWithBooks() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Autor> query = session.createQuery(
                    "SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros",
                    Autor.class
            );
            List<Autor> result = query.getResultList();
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
     * Update an author
     *
     * @param autor the author to update
     * @return the updated author
     */
    public Autor update(Autor autor) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Autor updatedAutor = session.merge(autor);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedAutor;
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
     * Delete an author by ID
     *
     * @param id the author ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Autor autor = session.get(Autor.class, id);
            if (autor != null) {
                session.remove(autor);
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