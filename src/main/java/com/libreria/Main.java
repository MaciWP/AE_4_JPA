package com.libreria;

import com.libreria.controller.MainController;
import com.libreria.database.HibernateUtil;

/**
 * Main class for the bookstore management system.
 */
public class Main {

    public static void main(String[] args) {

        try {
            MainController mainController = new MainController();

            try {
                mainController.start();
            } finally {
                mainController.close();
            }
        } catch (Exception e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}