-- Eliminar base de datos si existe y crearla de nuevo
DROP DATABASE IF EXISTS libreriadb;
CREATE DATABASE libreriadb;
USE libreriadb;

-- Crear tabla AUTORES
CREATE TABLE AUTORES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(200) NOT NULL,
    fecha_nacimiento DATE
) ENGINE=InnoDB;

-- Crear tabla EDITORIALES
CREATE TABLE EDITORIALES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255)
) ENGINE=InnoDB;

-- Crear tabla LIBROS
CREATE TABLE LIBROS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    precio DECIMAL(10,2),
    autor_id BIGINT NOT NULL,
    editorial_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES AUTORES(id),
    FOREIGN KEY (editorial_id) REFERENCES EDITORIALES(id)
) ENGINE=InnoDB;

-- Crear tabla LIBRERIAS
CREATE TABLE LIBRERIAS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nombre_dueno VARCHAR(100),
    direccion VARCHAR(255)
) ENGINE=InnoDB;

-- Crear tabla de unión LIBRERIA_LIBRO
CREATE TABLE LIBRERIA_LIBRO (
    libreria_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    PRIMARY KEY (libreria_id, libro_id),
    FOREIGN KEY (libreria_id) REFERENCES LIBRERIAS(id),
    FOREIGN KEY (libro_id) REFERENCES LIBROS(id)
) ENGINE=InnoDB;

-- Crear tabla DIRECCIONES
CREATE TABLE DIRECCIONES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(255) NOT NULL,
    ciudad VARCHAR(255) NOT NULL,
    codigo_postal VARCHAR(255),
    pais VARCHAR(255)
) ENGINE=InnoDB;

-- Crear tabla DEPARTAMENTOS
CREATE TABLE DEPARTAMENTOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    ubicacion VARCHAR(255)
) ENGINE=InnoDB;

-- Crear tabla PROYECTOS
CREATE TABLE PROYECTOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    fecha_inicio DATE,
    fecha_fin DATE,
    presupuesto DOUBLE
) ENGINE=InnoDB;

-- Crear tabla EMPLEADOS
CREATE TABLE EMPLEADOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    fecha_contratacion DATE,
    salario DOUBLE,
    direccion_id BIGINT UNIQUE,
    departamento_id BIGINT,
    FOREIGN KEY (direccion_id) REFERENCES DIRECCIONES(id),
    FOREIGN KEY (departamento_id) REFERENCES DEPARTAMENTOS(id)
) ENGINE=InnoDB;

-- Crear tabla de unión EMPLEADO_PROYECTO
CREATE TABLE EMPLEADO_PROYECTO (
    empleado_id BIGINT NOT NULL,
    proyecto_id BIGINT NOT NULL,
    PRIMARY KEY (empleado_id, proyecto_id),
    FOREIGN KEY (empleado_id) REFERENCES EMPLEADOS(id),
    FOREIGN KEY (proyecto_id) REFERENCES PROYECTOS(id)
) ENGINE=InnoDB;

-- Mostrar las tablas creadas
SHOW TABLES;