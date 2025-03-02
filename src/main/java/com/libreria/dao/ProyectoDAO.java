package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Proyecto;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Proyecto entity
 */
public class ProyectoDAO {

    private Session session;

    /**
     * Save a project to the database
     *
     * @param proyecto the project to save
     * @return the saved project with generated ID
     */
    public Proyecto save(Proyecto proyecto) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(proyecto);
            if (!existingTransaction) {
                tx.commit();
            }
            return proyecto;
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
     * Find a project by ID
     *
     * @param id the project ID
     * @return the found project or null if not found
     */
    public Proyecto findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Proyecto proyecto = session.get(Proyecto.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return proyecto;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all projects
     *
     * @return list of all projects
     */
    public List<Proyecto> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Proyecto> query = session.createNamedQuery("Proyecto.findAll", Proyecto.class);
            List<Proyecto> result = query.getResultList();
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
     * Find all projects with their employees
     *
     * @return list of projects with their employees
     */
    public List<Proyecto> findAllWithEmployees() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Proyecto> query = session.createQuery(
                    "SELECT DISTINCT p FROM Proyecto p LEFT JOIN FETCH p.empleados",
                    Proyecto.class
            );
            List<Proyecto> result = query.getResultList();
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
     * Update a project
     *
     * @param proyecto the project to update
     * @return the updated project
     */
    public Proyecto update(Proyecto proyecto) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Proyecto updatedProyecto = session.merge(proyecto);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedProyecto;
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
     * Delete a project by ID
     *
     * @param id the project ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Proyecto proyecto = session.get(Proyecto.class, id);
            if (proyecto != null) {
                session.remove(proyecto);
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