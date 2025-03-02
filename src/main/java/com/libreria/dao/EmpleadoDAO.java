package com.libreria.dao;

import com.libreria.database.HibernateUtil;
import com.libreria.model.Empleado;
import com.libreria.model.Proyecto;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Empleado entity
 */
public class EmpleadoDAO {

    private Session session;

    /**
     * Save an employee to the database
     *
     * @param empleado the employee to save
     * @return the saved employee with generated ID
     */
    public Empleado save(Empleado empleado) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            session.persist(empleado);
            if (!existingTransaction) {
                tx.commit();
            }
            return empleado;
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
     * Find an employee by ID
     *
     * @param id the employee ID
     * @return the found employee or null if not found
     */
    public Empleado findById(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Empleado empleado = session.get(Empleado.class, id);
            if (!existingTransaction) {
                tx.commit();
            }
            return empleado;
        } finally {
            if (!existingTransaction && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Get all employees
     *
     * @return list of all employees
     */
    public List<Empleado> findAll() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Empleado> query = session.createNamedQuery("Empleado.findAll", Empleado.class);
            List<Empleado> result = query.getResultList();
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
     * Find all employees with their department
     *
     * @return list of employees with their department
     */
    public List<Empleado> findAllWithDepartment() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Empleado> query = session.createQuery(
                    "SELECT DISTINCT e FROM Empleado e LEFT JOIN FETCH e.departamento",
                    Empleado.class
            );
            List<Empleado> result = query.getResultList();
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
     * Find all employees with their address
     *
     * @return list of employees with their address
     */
    public List<Empleado> findAllWithAddress() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Empleado> query = session.createQuery(
                    "SELECT DISTINCT e FROM Empleado e LEFT JOIN FETCH e.direccion",
                    Empleado.class
            );
            List<Empleado> result = query.getResultList();
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
     * Find all employees with their projects
     *
     * @return list of employees with their projects
     */
    public List<Empleado> findAllWithProjects() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Query<Empleado> query = session.createQuery(
                    "SELECT DISTINCT e FROM Empleado e LEFT JOIN FETCH e.proyectos",
                    Empleado.class
            );
            List<Empleado> result = query.getResultList();
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
     * Update an employee
     *
     * @param empleado the employee to update
     * @return the updated employee
     */
    public Empleado update(Empleado empleado) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Empleado updatedEmpleado = session.merge(empleado);
            if (!existingTransaction) {
                tx.commit();
            }
            return updatedEmpleado;
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
     * Delete an employee by ID
     *
     * @param id the employee ID to delete
     */
    public void delete(Long id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Empleado empleado = session.get(Empleado.class, id);
            if (empleado != null) {
                session.remove(empleado);
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
     * Assign a project to an employee
     *
     * @param empleadoId the employee ID
     * @param proyecto the project to assign
     * @return the updated employee
     */
    public Empleado assignProyecto(Long empleadoId, Proyecto proyecto) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        boolean existingTransaction = session.getTransaction().isActive();
        Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

        try {
            Empleado empleado = session.get(Empleado.class, empleadoId);
            if (empleado != null) {
                empleado.asignarProyecto(proyecto);
                session.merge(empleado);
            }
            if (!existingTransaction) {
                tx.commit();
            }
            return empleado;
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