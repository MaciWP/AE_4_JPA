package com.libreria.controller;

import java.util.Scanner;
import com.libreria.service.CompanyService;
import com.libreria.utils.Constants;

/**
 * Controller for the company management system.
 * Handles user interaction with the company module.
 */
public class CompanyController {

    private final Scanner scanner;
    private final CompanyService companyService;

    /**
     * Constructor that initializes the scanner and company service.
     *
     * @param scanner Shared scanner for reading user input
     */
    public CompanyController(Scanner scanner) {
        this.scanner = scanner;
        this.companyService = new CompanyService();
    }

    /**
     * Shows the company management menu and processes user selections.
     */
    public void showMenu() {
        boolean returnToMain = false;

        while (!returnToMain) {
            displayCompanyMenu();
            int option = readOption();

            switch (option) {
                case 1:
                    companyService.createCompanyData();
                    break;
                case 2:
                    companyService.demonstrateOneToOne();
                    break;
                case 3:
                    companyService.demonstrateOneToMany();
                    break;
                case 4:
                    companyService.demonstrateManyToMany();
                    break;
                case 5:
                    companyService.deleteAllData();
                    break;
                case 6:
                    returnToMain = true;
                    System.out.println(Constants.SUCCESS_RETURN_MAIN_MENU);
                    break;
                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
            }
        }
    }

    /**
     * Displays the options menu for the company system.
     */
    private void displayCompanyMenu() {
        System.out.println(Constants.COMPANY_MENU_TITLE);
        System.out.println(Constants.COMPANY_MENU_OPTION_CREATE_DATA);
        System.out.println(Constants.COMPANY_MENU_OPTION_SHOW_ONE_TO_ONE);
        System.out.println(Constants.COMPANY_MENU_OPTION_SHOW_ONE_TO_MANY);
        System.out.println(Constants.COMPANY_MENU_OPTION_SHOW_MANY_TO_MANY);
        System.out.println(Constants.COMPANY_MENU_OPTION_DELETE_DATA);
        System.out.println(Constants.COMPANY_MENU_OPTION_RETURN);
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