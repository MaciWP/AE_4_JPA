package com.libreria.service;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.libreria.dao.DepartamentoDAO;
import com.libreria.dao.DireccionDAO;
import com.libreria.dao.EmpleadoDAO;
import com.libreria.dao.ProyectoDAO;
import com.libreria.database.HibernateUtil;
import com.libreria.model.Departamento;
import com.libreria.model.Direccion;
import com.libreria.model.Empleado;
import com.libreria.model.Proyecto;
import com.libreria.utils.Constants;

/**
 * Service that manages operations related to Requirement 2 (Company Management System).
 * Contains the business logic for the company system and demonstrates the three types of relationships:
 * One-to-One, One-to-Many, and Many-to-Many.
 */
public class CompanyService {

    private final DepartamentoDAO departamentoDAO;
    private final EmpleadoDAO empleadoDAO;
    private final ProyectoDAO proyectoDAO;
    private final DireccionDAO direccionDAO;

    public CompanyService() {
        this.departamentoDAO = new DepartamentoDAO();
        this.empleadoDAO = new EmpleadoDAO();
        this.proyectoDAO = new ProyectoDAO();
        this.direccionDAO = new DireccionDAO();
    }

    /**
     * Checks if data already exists in the database
     * @return true if data exists, false otherwise
     */
    private boolean existsData() {
        List<Departamento> departamentos = departamentoDAO.findAll();
        return !departamentos.isEmpty();
    }

    /**
     * Creates sample data for the company model,
     * only if no previous data exists.
     */
    public void createCompanyData() {
        // Check if data already exists
        if (existsData()) {
            System.out.println(Constants.DATA_ALREADY_EXISTS);
            return;
        }

        try {
            // Create departments (One in One-to-Many with Employee)
            Departamento depTI = new Departamento(Constants.DEPT_IT_NAME, Constants.DEPT_IT_DESC, Constants.DEPT_IT_LOCATION);
            Departamento depRH = new Departamento(Constants.DEPT_HR_NAME, Constants.DEPT_HR_DESC, Constants.DEPT_HR_LOCATION);
            Departamento depMKT = new Departamento(Constants.DEPT_MKT_NAME, Constants.DEPT_MKT_DESC, Constants.DEPT_MKT_LOCATION);

            depTI = departamentoDAO.save(depTI);
            depRH = departamentoDAO.save(depRH);
            depMKT = departamentoDAO.save(depMKT);

            // Create projects (Many in Many-to-Many with Employee)
            Proyecto proyWeb = new Proyecto(Constants.PROJECT_WEB_NAME, Constants.PROJECT_WEB_DESC,
                    LocalDate.now(), LocalDate.now().plusMonths(Constants.PROJECT_WEB_DURATION_MONTHS),
                    Constants.PROJECT_WEB_BUDGET);
            Proyecto proyApp = new Proyecto(Constants.PROJECT_APP_NAME, Constants.PROJECT_APP_DESC,
                    LocalDate.now(), LocalDate.now().plusMonths(Constants.PROJECT_APP_DURATION_MONTHS),
                    Constants.PROJECT_APP_BUDGET);
            Proyecto proyCRM = new Proyecto(Constants.PROJECT_CRM_NAME, Constants.PROJECT_CRM_DESC,
                    LocalDate.now().minusMonths(Constants.PROJECT_CRM_START_MINUS_MONTHS),
                    LocalDate.now().plusMonths(Constants.PROJECT_CRM_DURATION_MONTHS),
                    Constants.PROJECT_CRM_BUDGET);

            proyWeb = proyectoDAO.save(proyWeb);
            proyApp = proyectoDAO.save(proyApp);
            proyCRM = proyectoDAO.save(proyCRM);

            // Create addresses (One in One-to-One with Employee)
            Direccion dir1 = new Direccion(Constants.ADDRESS_1_STREET, Constants.ADDRESS_1_CITY,
                    Constants.ADDRESS_1_ZIP, Constants.ADDRESS_1_COUNTRY);
            Direccion dir2 = new Direccion(Constants.ADDRESS_2_STREET, Constants.ADDRESS_2_CITY,
                    Constants.ADDRESS_2_ZIP, Constants.ADDRESS_2_COUNTRY);
            Direccion dir3 = new Direccion(Constants.ADDRESS_3_STREET, Constants.ADDRESS_3_CITY,
                    Constants.ADDRESS_3_ZIP, Constants.ADDRESS_3_COUNTRY);
            Direccion dir4 = new Direccion(Constants.ADDRESS_4_STREET, Constants.ADDRESS_4_CITY,
                    Constants.ADDRESS_4_ZIP, Constants.ADDRESS_4_COUNTRY);

            // Create employee 1 with relationships
            Empleado emp1 = new Empleado(Constants.EMPLOYEE_1_NAME, Constants.EMPLOYEE_1_LASTNAME,
                    Constants.EMPLOYEE_1_EMAIL,
                    LocalDate.of(Constants.EMPLOYEE_1_HIRE_YEAR,
                            Constants.EMPLOYEE_1_HIRE_MONTH,
                            Constants.EMPLOYEE_1_HIRE_DAY),
                    Constants.EMPLOYEE_1_SALARY);
            // Assign address and department before saving
            emp1.asignarDireccion(dir1);
            emp1.asignarDepartamento(depTI);
            emp1 = empleadoDAO.save(emp1);

            // Assign projects after saving the employee
            emp1 = empleadoDAO.assignProyecto(emp1.getId(), proyWeb);
            emp1 = empleadoDAO.assignProyecto(emp1.getId(), proyApp);

            // Create employee 2 with relationships
            Empleado emp2 = new Empleado(Constants.EMPLOYEE_2_NAME, Constants.EMPLOYEE_2_LASTNAME,
                    Constants.EMPLOYEE_2_EMAIL,
                    LocalDate.of(Constants.EMPLOYEE_2_HIRE_YEAR,
                            Constants.EMPLOYEE_2_HIRE_MONTH,
                            Constants.EMPLOYEE_2_HIRE_DAY),
                    Constants.EMPLOYEE_2_SALARY);
            emp2.asignarDireccion(dir2);
            emp2.asignarDepartamento(depTI);
            emp2 = empleadoDAO.save(emp2);
            emp2 = empleadoDAO.assignProyecto(emp2.getId(), proyApp);

            // Create employee 3 with relationships
            Empleado emp3 = new Empleado(Constants.EMPLOYEE_3_NAME, Constants.EMPLOYEE_3_LASTNAME,
                    Constants.EMPLOYEE_3_EMAIL,
                    LocalDate.of(Constants.EMPLOYEE_3_HIRE_YEAR,
                            Constants.EMPLOYEE_3_HIRE_MONTH,
                            Constants.EMPLOYEE_3_HIRE_DAY),
                    Constants.EMPLOYEE_3_SALARY);
            emp3.asignarDireccion(dir3);
            emp3.asignarDepartamento(depRH);
            emp3 = empleadoDAO.save(emp3);
            emp3 = empleadoDAO.assignProyecto(emp3.getId(), proyCRM);

            // Create employee 4 with relationships
            Empleado emp4 = new Empleado(Constants.EMPLOYEE_4_NAME, Constants.EMPLOYEE_4_LASTNAME,
                    Constants.EMPLOYEE_4_EMAIL,
                    LocalDate.of(Constants.EMPLOYEE_4_HIRE_YEAR,
                            Constants.EMPLOYEE_4_HIRE_MONTH,
                            Constants.EMPLOYEE_4_HIRE_DAY),
                    Constants.EMPLOYEE_4_SALARY);
            emp4.asignarDireccion(dir4);
            emp4.asignarDepartamento(depMKT);
            emp4 = empleadoDAO.save(emp4);
            emp4 = empleadoDAO.assignProyecto(emp4.getId(), proyWeb);
            emp4 = empleadoDAO.assignProyecto(emp4.getId(), proyCRM);

            System.out.println(Constants.SUCCESS_DATA_CREATED);
        } catch (Exception e) {
            System.err.println(String.format(Constants.ERROR_DATA_CREATION, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Deletes all data from the database related to the company model
     */
    public void deleteAllData() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("DELETE FROM EMPLEADO_PROYECTO", Void.class).executeUpdate();
            tx.commit();
            session.close();

            List<Empleado> empleados = empleadoDAO.findAll();
            for (Empleado empleado : empleados) {
                empleadoDAO.delete(empleado.getId());
            }

            List<Direccion> direcciones = direccionDAO.findAll();
            for (Direccion direccion : direcciones) {
                direccionDAO.delete(direccion.getId());
            }

            List<Proyecto> proyectos = proyectoDAO.findAll();
            for (Proyecto proyecto : proyectos) {
                proyectoDAO.delete(proyecto.getId());
            }

            List<Departamento> departamentos = departamentoDAO.findAll();
            for (Departamento departamento : departamentos) {
                departamentoDAO.delete(departamento.getId());
            }

            System.out.println(Constants.SUCCESS_DATA_DELETED);
        } catch (Exception e) {
            System.err.println(String.format(Constants.ERROR_DATA_DELETION, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Demonstrates a One-to-One relationship between Employee and Address using DAO.
     */
    public void demonstrateOneToOne() {
        System.out.println(Constants.QUERY_TITLE_ONE_TO_ONE);

        List<Empleado> empleados = empleadoDAO.findAllWithAddress();

        for (Empleado empleado : empleados) {
            Direccion direccion = empleado.getDireccion();
            System.out.println(String.format(
                    Constants.DISPLAY_EMPLOYEE,
                    empleado.getNombre(),
                    empleado.getApellido()
            ));
            System.out.println(String.format(
                    Constants.DISPLAY_ADDRESS,
                    direccion.getCalle(),
                    direccion.getCiudad(),
                    direccion.getCodigoPostal(),
                    direccion.getPais()
            ));
            System.out.println();
        }
    }

    /**
     * Demonstrates a One-to-Many relationship between Department and Employees using DAO.
     */
    public void demonstrateOneToMany() {
        System.out.println(Constants.QUERY_TITLE_ONE_TO_MANY);

        List<Departamento> departamentos = departamentoDAO.findAllWithEmployees();

        for (Departamento departamento : departamentos) {
            System.out.println(String.format(
                    Constants.DISPLAY_DEPARTMENT,
                    departamento.getNombre(),
                    departamento.getDescripcion(),
                    departamento.getUbicacion()
            ));

            if (departamento.getEmpleados().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_EMPLOYEES);
            } else {
                System.out.println(Constants.DISPLAY_EMPLOYEES);
                for (Empleado empleado : departamento.getEmpleados()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_EMPLOYEE_DETAIL,
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getFechaContratacion()
                    ));
                }
            }
            System.out.println();
        }
    }

    /**
     * Demonstrates a Many-to-Many relationship between Employee and Project using DAO.
     */
    public void demonstrateManyToMany() {
        System.out.println(Constants.QUERY_TITLE_MANY_TO_MANY);

        // Show all projects with their employees
        List<Proyecto> proyectos = proyectoDAO.findAllWithEmployees();

        System.out.println(Constants.TITLE_PROJECTS_WITH_EMPLOYEES);
        for (Proyecto proyecto : proyectos) {
            System.out.println(String.format(
                    Constants.DISPLAY_PROJECT,
                    proyecto.getNombre(),
                    proyecto.getDescripcion(),
                    proyecto.getPresupuesto()
            ));

            if (proyecto.getEmpleados().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_ASSIGNED_EMPLOYEES);
            } else {
                System.out.println(Constants.DISPLAY_ASSIGNED_EMPLOYEES);
                for (Empleado empleado : proyecto.getEmpleados()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_EMPLOYEE_EMAIL,
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getEmail()
                    ));
                }
            }
            System.out.println();
        }

        // Show all employees with their projects
        List<Empleado> empleados = empleadoDAO.findAllWithProjects();

        System.out.println(Constants.TITLE_EMPLOYEES_WITH_PROJECTS);
        for (Empleado empleado : empleados) {
            System.out.println(String.format(
                    Constants.DISPLAY_EMPLOYEE_PROJECTS,
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getEmail()
            ));

            if (empleado.getProyectos().isEmpty()) {
                System.out.println(Constants.DISPLAY_NO_PROJECTS);
            } else {
                System.out.println(Constants.DISPLAY_ASSIGNED_PROJECTS);
                for (Proyecto proyecto : empleado.getProyectos()) {
                    System.out.println(String.format(
                            Constants.DISPLAY_PROJECT_DETAIL,
                            proyecto.getNombre(),
                            proyecto.getDescripcion()
                    ));
                }
            }
            System.out.println();
        }
    }
}