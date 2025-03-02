package com.libreria.utils;

/**
 * Utility class containing all constants and messages for the application.
 */
public class Constants {

    // Main menu options
    public static final int OPTION_BOOKSTORE = 1;
    public static final int OPTION_COMPANY = 2;
    public static final int OPTION_EXIT = 3;

    // Menu titles
    public static final String MAIN_MENU_TITLE = "\n===== SISTEMA DE GESTIÓN JPA =====";
    public static final String BOOKSTORE_MENU_TITLE = "\n===== GESTIÓN DE LIBRERÍA =====";
    public static final String COMPANY_MENU_TITLE = "\n===== GESTIÓN DE EMPRESA =====";

    // Main menu texts
    public static final String MAIN_MENU_OPTION_BOOKSTORE = "1. Gestión de Librería (Requerimiento 1)";
    public static final String MAIN_MENU_OPTION_COMPANY = "2. Gestión de Empresa (Requerimiento 2)";
    public static final String MAIN_MENU_OPTION_EXIT = "3. Salir del sistema";
    public static final String MAIN_MENU_PROMPT = "Seleccione una opción: ";

    // Bookstore menu texts
    public static final String BOOKSTORE_MENU_OPTION_CREATE_DATA = "1. Crear datos de ejemplo";
    public static final String BOOKSTORE_MENU_OPTION_SHOW_BOOKS = "2. Mostrar libros con editorial y autor";
    public static final String BOOKSTORE_MENU_OPTION_SHOW_AUTHORS = "3. Mostrar autores con sus libros";
    public static final String BOOKSTORE_MENU_OPTION_SHOW_BOOKSTORES = "4. Mostrar librerías con sus libros";
    public static final String BOOKSTORE_MENU_OPTION_SHOW_BOOKS_WITH_BOOKSTORES = "5. Mostrar libros y sus librerías";
    public static final String BOOKSTORE_MENU_OPTION_DELETE_DATA = "6. Eliminar todos los datos";
    public static final String BOOKSTORE_MENU_OPTION_RETURN = "7. Volver al menú principal";

    // Company menu texts
    public static final String COMPANY_MENU_OPTION_CREATE_DATA = "1. Crear datos de ejemplo";
    public static final String COMPANY_MENU_OPTION_SHOW_ONE_TO_ONE = "2. Mostrar relación Uno a Uno (Empleado-Dirección)";
    public static final String COMPANY_MENU_OPTION_SHOW_ONE_TO_MANY = "3. Mostrar relación Uno a Muchos (Departamento-Empleados)";
    public static final String COMPANY_MENU_OPTION_SHOW_MANY_TO_MANY = "4. Mostrar relación Muchos a Muchos (Empleado-Proyecto)";
    public static final String COMPANY_MENU_OPTION_DELETE_DATA = "5. Eliminar todos los datos";
    public static final String COMPANY_MENU_OPTION_RETURN = "6. Volver al menú principal";

    // Success messages
    public static final String SUCCESS_DATA_CREATED = "¡Datos creados con éxito!";
    public static final String SUCCESS_DATA_DELETED = "¡Datos eliminados con éxito!";
    public static final String SUCCESS_OPERATION = "Operación completada con éxito";
    public static final String SUCCESS_RETURN_MAIN_MENU = "Volviendo al menú principal...";
    public static final String SUCCESS_EXIT = "¡Hasta pronto!";
    public static final String DATA_ALREADY_EXISTS = "Ya existen datos en la base de datos. No se crearán nuevos datos.";

    // Error messages
    public static final String ERROR_INVALID_OPTION = "Error: Opción no válida";
    public static final String ERROR_DATABASE = "Error en la base de datos: %s";
    public static final String ERROR_DATA_CREATION = "Error al crear los datos: %s";
    public static final String ERROR_DATA_DELETION = "Error al eliminar los datos: %s";
    public static final String ERROR_QUERY_EXECUTION = "Error al ejecutar la consulta: %s";
    public static final String ERROR_UNEXPECTED = "Error inesperado: %s";

    // Query titles
    public static final String QUERY_TITLE_BOOKS_WITH_PUBLISHER_AUTHOR = "\n===== CONSULTA 1: TODOS LOS LIBROS CON SU EDITORIAL Y AUTOR =====";
    public static final String QUERY_TITLE_AUTHORS_WITH_BOOKS = "\n===== CONSULTA 2: TODOS LOS AUTORES CON SUS LIBROS ASOCIADOS =====";
    public static final String QUERY_TITLE_BOOKSTORES_WITH_BOOKS = "\n===== CONSULTA 3: TODAS LAS LIBRERÍAS CON SUS LIBROS ASOCIADOS =====";
    public static final String QUERY_TITLE_BOOKS_WITH_BOOKSTORES = "\n===== CONSULTA 4: TODOS LOS LIBROS Y EN QUÉ LIBRERÍA ESTÁN =====";

    public static final String QUERY_TITLE_ONE_TO_ONE = "\n===== RELACIÓN UNO A UNO (Empleado-Dirección) =====";
    public static final String QUERY_TITLE_ONE_TO_MANY = "\n===== RELACIÓN UNO A MUCHOS (Departamento-Empleados) =====";
    public static final String QUERY_TITLE_MANY_TO_MANY = "\n===== RELACIÓN MUCHOS A MUCHOS (Proyecto-Empleados) =====";

    public static final String TITLE_PROJECTS_WITH_EMPLOYEES = "--- Proyectos y sus Empleados: ---";
    public static final String TITLE_EMPLOYEES_WITH_PROJECTS = "--- Empleados y sus Proyectos: ---";

    // Data display messages
    public static final String DISPLAY_BOOK = "Libro: %s - Precio: %s€ - Editorial: %s - Autor: %s %s";
    public static final String DISPLAY_AUTHOR = "Autor: %s %s";
    public static final String DISPLAY_NO_BOOKS = "  No tiene libros asociados";
    public static final String DISPLAY_BOOK_DETAIL = "  - %s (%s€)";
    public static final String DISPLAY_BOOKSTORE = "Librería: %s - Dueño: %s - Dirección: %s";
    public static final String DISPLAY_NO_INVENTORY = "  No tiene libros en inventario";
    public static final String DISPLAY_BOOK_IN_STORE = "  - %s";
    public static final String DISPLAY_BOOK_AVAILABILITY = "Libro: %s (%s€)";
    public static final String DISPLAY_NO_BOOKSTORE = "  No está disponible en ninguna librería";
    public static final String DISPLAY_BOOKSTORE_NAME = "  - Librería: %s";

    public static final String DISPLAY_EMPLOYEE = "Empleado: %s %s";
    public static final String DISPLAY_ADDRESS = "Dirección: %s, %s, %s, %s";
    public static final String DISPLAY_DEPARTMENT = "Departamento: %s - %s - Ubicación: %s";
    public static final String DISPLAY_NO_EMPLOYEES = "  No tiene empleados asignados";
    public static final String DISPLAY_EMPLOYEES = "  Empleados:";
    public static final String DISPLAY_EMPLOYEE_DETAIL = "  - %s %s - Contratado: %s";
    public static final String DISPLAY_PROJECT = "Proyecto: %s - %s - Presupuesto: %s€";
    public static final String DISPLAY_NO_ASSIGNED_EMPLOYEES = "  No tiene empleados asignados";
    public static final String DISPLAY_ASSIGNED_EMPLOYEES = "  Empleados asignados:";
    public static final String DISPLAY_EMPLOYEE_EMAIL = "  - %s %s - %s";
    public static final String DISPLAY_EMPLOYEE_PROJECTS = "Empleado: %s %s - %s";
    public static final String DISPLAY_NO_PROJECTS = "  No está asignado a ningún proyecto";
    public static final String DISPLAY_ASSIGNED_PROJECTS = "  Proyectos asignados:";
    public static final String DISPLAY_PROJECT_DETAIL = "  - %s - %s";

    // Sample data constants - Authors
    public static final String AUTHOR_1_NAME = "Gabriel";
    public static final String AUTHOR_1_LASTNAME = "García Márquez";
    public static final String AUTHOR_2_NAME = "Isabel";
    public static final String AUTHOR_2_LASTNAME = "Allende";
    public static final String AUTHOR_3_NAME = "Mario";
    public static final String AUTHOR_3_LASTNAME = "Vargas Llosa";

    // Sample data constants - Publishers
    public static final String PUBLISHER_1_NAME = "Penguin Random House";
    public static final String PUBLISHER_1_ADDRESS = "Calle Gran Vía 32, Madrid";
    public static final String PUBLISHER_2_NAME = "Planeta";
    public static final String PUBLISHER_2_ADDRESS = "Avenida Diagonal 662, Barcelona";

    // Sample data constants - Books
    public static final String BOOK_1_TITLE = "Cien años de soledad";
    public static final String BOOK_1_PRICE = "25.99";
    public static final String BOOK_2_TITLE = "El amor en los tiempos del cólera";
    public static final String BOOK_2_PRICE = "22.50";
    public static final String BOOK_3_TITLE = "La casa de los espíritus";
    public static final String BOOK_3_PRICE = "21.75";
    public static final String BOOK_4_TITLE = "Eva Luna";
    public static final String BOOK_4_PRICE = "19.99";
    public static final String BOOK_5_TITLE = "La ciudad y los perros";
    public static final String BOOK_5_PRICE = "24.50";
    public static final String BOOK_6_TITLE = "La fiesta del chivo";
    public static final String BOOK_6_PRICE = "26.75";
    public static final String BOOK_7_TITLE = "Crónica de una muerte anunciada";
    public static final String BOOK_7_PRICE = "18.99";
    public static final String BOOK_8_TITLE = "El otoño del patriarca";
    public static final String BOOK_8_PRICE = "20.25";

    // Sample data constants - Bookstores
    public static final String BOOKSTORE_1_NAME = "El Rincón Literario";
    public static final String BOOKSTORE_1_OWNER = "Ana Gómez";
    public static final String BOOKSTORE_1_ADDRESS = "Calle Mayor 12, Madrid";
    public static final String BOOKSTORE_2_NAME = "Libros y Más";
    public static final String BOOKSTORE_2_OWNER = "Carlos Ruiz";
    public static final String BOOKSTORE_2_ADDRESS = "Avenida Libertad 45, Barcelona";

    // Sample data constants - Departments
    public static final String DEPT_IT_NAME = "Tecnología";
    public static final String DEPT_IT_DESC = "Departamento de Tecnología e Innovación";
    public static final String DEPT_IT_LOCATION = "Planta 3";
    public static final String DEPT_HR_NAME = "RRHH";
    public static final String DEPT_HR_DESC = "Recursos Humanos";
    public static final String DEPT_HR_LOCATION = "Planta 2";
    public static final String DEPT_MKT_NAME = "Marketing";
    public static final String DEPT_MKT_DESC = "Departamento de Marketing y Ventas";
    public static final String DEPT_MKT_LOCATION = "Planta 1";

    // Sample data constants - Projects
    public static final String PROJECT_WEB_NAME = "Web Corporativa";
    public static final String PROJECT_WEB_DESC = "Rediseño de la web corporativa";
    public static final String PROJECT_APP_NAME = "App Móvil";
    public static final String PROJECT_APP_DESC = "Desarrollo de app para clientes";
    public static final String PROJECT_CRM_NAME = "CRM";
    public static final String PROJECT_CRM_DESC = "Implementación de sistema CRM";

    // Sample data constants - Addresses
    public static final String ADDRESS_1_STREET = "Calle Mayor 10";
    public static final String ADDRESS_1_CITY = "Madrid";
    public static final String ADDRESS_1_ZIP = "28001";
    public static final String ADDRESS_1_COUNTRY = "España";
    public static final String ADDRESS_2_STREET = "Avenida Diagonal 100";
    public static final String ADDRESS_2_CITY = "Barcelona";
    public static final String ADDRESS_2_ZIP = "08018";
    public static final String ADDRESS_2_COUNTRY = "España";
    public static final String ADDRESS_3_STREET = "Plaza Nueva 5";
    public static final String ADDRESS_3_CITY = "Sevilla";
    public static final String ADDRESS_3_ZIP = "41001";
    public static final String ADDRESS_3_COUNTRY = "España";
    public static final String ADDRESS_4_STREET = "Gran Vía 30";
    public static final String ADDRESS_4_CITY = "Madrid";
    public static final String ADDRESS_4_ZIP = "28013";
    public static final String ADDRESS_4_COUNTRY = "España";

    // Sample data constants - Employees
    public static final String EMPLOYEE_1_NAME = "Juan";
    public static final String EMPLOYEE_1_LASTNAME = "Pérez";
    public static final String EMPLOYEE_1_EMAIL = "juan.perez@empresa.com";
    public static final String EMPLOYEE_2_NAME = "María";
    public static final String EMPLOYEE_2_LASTNAME = "López";
    public static final String EMPLOYEE_2_EMAIL = "maria.lopez@empresa.com";
    public static final String EMPLOYEE_3_NAME = "Carlos";
    public static final String EMPLOYEE_3_LASTNAME = "Gómez";
    public static final String EMPLOYEE_3_EMAIL = "carlos.gomez@empresa.com";
    public static final String EMPLOYEE_4_NAME = "Ana";
    public static final String EMPLOYEE_4_LASTNAME = "Martínez";
    public static final String EMPLOYEE_4_EMAIL = "ana.martinez@empresa.com";

    // Fechas de nacimiento de autores
    public static final int AUTHOR_1_BIRTH_YEAR = 1927;
    public static final int AUTHOR_1_BIRTH_MONTH = 3;
    public static final int AUTHOR_1_BIRTH_DAY = 6;

    public static final int AUTHOR_2_BIRTH_YEAR = 1942;
    public static final int AUTHOR_2_BIRTH_MONTH = 8;
    public static final int AUTHOR_2_BIRTH_DAY = 2;

    public static final int AUTHOR_3_BIRTH_YEAR = 1936;
    public static final int AUTHOR_3_BIRTH_MONTH = 3;
    public static final int AUTHOR_3_BIRTH_DAY = 28;

    // Presupuestos de proyectos
    public static final double PROJECT_WEB_BUDGET = 25000.0;
    public static final double PROJECT_APP_BUDGET = 45000.0;
    public static final double PROJECT_CRM_BUDGET = 35000.0;

    // Duración de proyectos (en meses)
    public static final int PROJECT_WEB_DURATION_MONTHS = 3;
    public static final int PROJECT_APP_DURATION_MONTHS = 6;
    public static final int PROJECT_CRM_DURATION_MONTHS = 4;
    public static final int PROJECT_CRM_START_MINUS_MONTHS = 1;

    // Fechas de contratación de empleados
    public static final int EMPLOYEE_1_HIRE_YEAR = 2020;
    public static final int EMPLOYEE_1_HIRE_MONTH = 1;
    public static final int EMPLOYEE_1_HIRE_DAY = 15;

    public static final int EMPLOYEE_2_HIRE_YEAR = 2019;
    public static final int EMPLOYEE_2_HIRE_MONTH = 5;
    public static final int EMPLOYEE_2_HIRE_DAY = 10;

    public static final int EMPLOYEE_3_HIRE_YEAR = 2021;
    public static final int EMPLOYEE_3_HIRE_MONTH = 3;
    public static final int EMPLOYEE_3_HIRE_DAY = 22;

    public static final int EMPLOYEE_4_HIRE_YEAR = 2018;
    public static final int EMPLOYEE_4_HIRE_MONTH = 11;
    public static final int EMPLOYEE_4_HIRE_DAY = 5;

    // Salarios de empleados
    public static final double EMPLOYEE_1_SALARY = 45000.0;
    public static final double EMPLOYEE_2_SALARY = 48000.0;
    public static final double EMPLOYEE_3_SALARY = 38000.0;
    public static final double EMPLOYEE_4_SALARY = 52000.0;

    // Prevent instantiation
    private Constants() {
    }
}