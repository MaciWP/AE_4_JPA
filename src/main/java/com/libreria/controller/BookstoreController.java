package com.libreria.controller;

import java.util.Scanner;
import com.libreria.service.BookstoreService;
import com.libreria.utils.Constants;

/**
 * Controller for the bookstore management system.
 * Handles user interaction with the bookstore module.
 */
public class BookstoreController {

    private final Scanner scanner;
    private final BookstoreService bookstoreService;

    /**
     * Constructor that initializes the scanner and bookstore service.
     *
     * @param scanner Shared scanner for reading user input
     */
    public BookstoreController(Scanner scanner) {
        this.scanner = scanner;
        this.bookstoreService = new BookstoreService();
    }

    /**
     * Shows the bookstore management menu and processes user selections.
     */
    public void showMenu() {
        boolean returnToMain = false;

        while (!returnToMain) {
            displayBookstoreMenu();
            int option = readOption();

            switch (option) {
                case 1:
                    bookstoreService.createBookstoreData();
                    break;
                case 2:
                    bookstoreService.showAllBooksWithPublisherAndAuthor();
                    break;
                case 3:
                    bookstoreService.showAllAuthorsWithBooks();
                    break;
                case 4:
                    bookstoreService.showAllBookstoresWithBooks();
                    break;
                case 5:
                    bookstoreService.showAllBooksWithBookstores();
                    break;
                case 6:
                    bookstoreService.deleteAllData();
                    break;
                case 7:
                    returnToMain = true;
                    System.out.println(Constants.SUCCESS_RETURN_MAIN_MENU);
                    break;
                default:
                    System.out.println(Constants.ERROR_INVALID_OPTION);
            }
        }
    }

    /**
     * Displays the options menu for the bookstore system.
     */
    private void displayBookstoreMenu() {
        System.out.println(Constants.BOOKSTORE_MENU_TITLE);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_CREATE_DATA);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_SHOW_BOOKS);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_SHOW_AUTHORS);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_SHOW_BOOKSTORES);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_SHOW_BOOKS_WITH_BOOKSTORES);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_DELETE_DATA);
        System.out.println(Constants.BOOKSTORE_MENU_OPTION_RETURN);
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