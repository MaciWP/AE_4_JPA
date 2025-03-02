package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Direccion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Direccion entity
 */
public class DireccionDAO {

    private Session session;

    /**
     * Save an address to the database
     *
     * @param direccion the address to save
     * @return the saved address with generated ID
     */
    public Direccion save(Direccion direccion) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(direccion);
            if (!existingTransaction) {
                tx.commit();
            }
            return direccion;
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
     * Find an address by ID
     *
     * @param id the address ID
     * @return the found address or null if not found
     */
    public Direccion findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Direccion direccion = session.get(Direccion.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return direccion;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all addresses
     *
     * @return list of all addresses
     */
    public List<Direccion> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Direccion> query = session.createNamedQuery("Direccion.findAll", Direccion.class);
            List<Direccion> result = query.getResultList();
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
     * Update an address
     *
     * @param direccion the address to update
     * @return the updated address
     */
    public Direccion update(Direccion direccion) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Direccion updatedDireccion = session.merge(direccion);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedDireccion;
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
     * Delete an address by ID
     *
     * @param id the address ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Direccion direccion = session.get(Direccion.class, id);
            if (direccion != null) {
                session.remove(direccion);
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