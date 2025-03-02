package com.libreria.controller;

import java.util.Scanner;
import com.libreria.utils.Constants;

/**
 * Main controller that coordinates navigation between the different modules of the application.
 */
public class MainController {

    private final Scanner scanner;
    private final BookstoreController bookstoreController;
    private final CompanyController companyController;

    /**
     * Constructor that initializes the secondary controllers and the scanner for user input.
     */
    public MainController() {
        this.scanner = new Scanner(System.in);
        this.bookstoreController = new BookstoreController(scanner);
        this.companyController = new CompanyController(scanner);
    }

    /**
     * Starts the main controller and runs the application's main loop.
     */
    public void start() {
        boolean exit = false;

        try {
            while (!exit) {
                displayMainMenu();
                int option = readOption();

                switch (option) {
                    case Constants.OPTION_BOOKSTORE:
                        bookstoreController.showMenu();
                        break;
                    case Constants.OPTION_COMPANY:
                        companyController.showMenu();
                        break;
                    case Constants.OPTION_EXIT:
                        exit = true;
                        System.out.println(Constants.SUCCESS_EXIT);
                        break;
                    default:
                        System.out.println(Constants.ERROR_INVALID_OPTION);
                }
            }
        } catch (Exception e) {
            System.err.println(String.format(Constants.ERROR_UNEXPECTED, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Closes the resources used by the controller.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Displays the main menu of the application.
     */
    private void displayMainMenu() {
        System.out.println(Constants.MAIN_MENU_TITLE);
        System.out.println(Constants.MAIN_MENU_OPTION_BOOKSTORE);
        System.out.println(Constants.MAIN_MENU_OPTION_COMPANY);
        System.out.println(Constants.MAIN_MENU_OPTION_EXIT);
        System.out.print(Constants.MAIN_MENU_PROMPT);
    }

    /**
     * Reads an option from the user.
     *
     * @return the selected option
     */
    private int readOption() {
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            option = -1;
        }
        return option;
    }
}