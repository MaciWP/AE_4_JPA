-- File: sql/check_tables_with_separator_output.sql
-- Seleccionar la base de datos
USE libreriadb;

-- Grupo 1: Verificar datos en las primeras tablas
SELECT COUNT(*) AS total_autores FROM AUTORES;
SELECT COUNT(*) AS total_editoriales FROM EDITORIALES;
SELECT COUNT(*) AS total_libros FROM LIBROS;
SELECT COUNT(*) AS total_librerias FROM LIBRERIAS;
SELECT COUNT(*) AS total_libreria_libro FROM LIBRERIA_LIBRO;

-- Separador en la salida (se imprimirá como una fila)
SELECT '--------------------------' AS line_separator;

-- Grupo 2: Verificar datos en las siguientes tablas
SELECT COUNT(*) AS total_direcciones FROM DIRECCIONES;
SELECT COUNT(*) AS total_departamentos FROM DEPARTAMENTOS;
SELECT COUNT(*) AS total_proyectos FROM PROYECTOS;
SELECT COUNT(*) AS total_empleados FROM EMPLEADOS;
SELECT COUNT(*) AS total_empleado_proyecto FROM EMPLEADO_PROYECTO;