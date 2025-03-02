package com.libreria.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility for managing Hibernate sessions.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    /**
     * Gets the Hibernate session factory.
     * Created only once for the application lifecycle.
     *
     * @return SessionFactory instance
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                System.err.println("Error creating SessionFactory: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return sessionFactory;
    }

    /**
     * Gets a Hibernate session.
     *
     * @return a Hibernate session
     */
    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    /**
     * Tests the database connection.
     *
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery("SELECT 1", Integer.class).getSingleResult();
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error testing connection: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Closes the session factory.
     * Should be called when the application finishes.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}