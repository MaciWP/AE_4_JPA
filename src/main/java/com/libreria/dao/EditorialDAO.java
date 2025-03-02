package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Editorial;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Editorial entity
 */
public class EditorialDAO {

    private Session session;

    /**
     * Save a publisher to the database
     *
     * @param editorial the publisher to save
     * @return the saved publisher with generated ID
     */
    public Editorial save(Editorial editorial) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(editorial);
            if (!existingTransaction) {
                tx.commit();
            }
            return editorial;
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
     * Find a publisher by ID
     *
     * @param id the publisher ID
     * @return the found publisher or null if not found
     */
    public Editorial findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Editorial editorial = session.get(Editorial.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return editorial;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all publishers
     *
     * @return list of all publishers
     */
    public List<Editorial> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Editorial> query = session.createNamedQuery("Editorial.findAll", Editorial.class);
            List<Editorial> result = query.getResultList();
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
     * Find all publishers with their associated books
     *
     * @return list of publishers with their books
     */
    public List<Editorial> findAllWithBooks() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Editorial> query = session.createQuery(
                    "SELECT DISTINCT e FROM Editorial e LEFT JOIN FETCH e.librosPublicados",
                    Editorial.class
            );
            List<Editorial> result = query.getResultList();
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
     * Update a publisher
     *
     * @param editorial the publisher to update
     * @return the updated publisher
     */
    public Editorial update(Editorial editorial) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Editorial updatedEditorial = session.merge(editorial);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedEditorial;
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
     * Delete a publisher by ID
     *
     * @param id the publisher ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Editorial editorial = session.get(Editorial.class, id);
            if (editorial != null) {
                session.remove(editorial);
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