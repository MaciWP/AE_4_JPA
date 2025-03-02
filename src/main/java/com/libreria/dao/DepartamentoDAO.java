package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Departamento;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Departamento entity
 */
public class DepartamentoDAO {

    private Session session;

    /**
     * Save a department to the database
     *
     * @param departamento the department to save
     * @return the saved department with generated ID
     */
    public Departamento save(Departamento departamento) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(departamento);
            if (!existingTransaction) {
                tx.commit();
            }
            return departamento;
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
     * Find a department by ID
     *
     * @param id the department ID
     * @return the found department or null if not found
     */
    public Departamento findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Departamento departamento = session.get(Departamento.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return departamento;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all departments
     *
     * @return list of all departments
     */
    public List<Departamento> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Departamento> query = session.createNamedQuery("Departamento.findAll", Departamento.class);
            List<Departamento> result = query.getResultList();
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
     * Find all departments with their employees
     *
     * @return list of departments with their employees
     */
    public List<Departamento> findAllWithEmployees() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Departamento> query = session.createQuery(
                    "SELECT DISTINCT d FROM Departamento d LEFT JOIN FETCH d.empleados",
                    Departamento.class
            );
            List<Departamento> result = query.getResultList();
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
     * Update a department
     *
     * @param departamento the department to update
     * @return the updated department
     */
    public Departamento update(Departamento departamento) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Departamento updatedDepartamento = session.merge(departamento);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedDepartamento;
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
     * Delete a department by ID
     *
     * @param id the department ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Departamento departamento = session.get(Departamento.class, id);
            if (departamento != null) {
                session.remove(departamento);
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