# 📚 Sistema de Gestión de Librerías y Empresa con JPA/Hibernate

![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL Badge](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Hibernate Badge](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![Maven Badge](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

## 📑 Índice
1. [Descripción y Objetivos](#-descripción-y-objetivos)
2. [Arquitectura y Diseño](#-arquitectura-y-diseño)
3. [Modelo de Datos](#-modelo-de-datos)
4. [Patrones Implementados](#-patrones-implementados)
5. [Gestión de Relaciones](#-gestión-de-relaciones)
6. [Validaciones y Manejo de Errores](#-validaciones-y-manejo-de-errores)
7. [Resultados y Evidencias](#-resultados-y-evidencias)
8. [Configuración e Instalación](#-configuración-e-instalación)
9. [Conclusiones](#-conclusiones)

## 📋 Descripción y Objetivos

Este sistema implementa dos modelos de negocio utilizando JPA/Hibernate como tecnología de persistencia:

1. **Requerimiento 1**: Modelo de datos para gestionar una cadena de librerías, incluyendo autores, editoriales, libros y librerías, con relaciones bidireccionales.

2. **Requerimiento 2**: Modelo empresarial que demuestra los tres tipos de relaciones (1:1, 1:N, N:M) utilizando empleados, departamentos, direcciones y proyectos.

### 🎯 Objetivos Principales

- Implementar las entidades JPA con sus relaciones bidireccionales
- Demostrar el funcionamiento de los tres tipos de relaciones
- Crear consultas que evidencien el funcionamiento correcto de las relaciones
- Proporcionar una interfaz de usuario mediante consola para interactuar con el sistema

## 🏗 Arquitectura y Diseño

### 📐 Estructura del Sistema

```ascii
Usuario
   ↓
MainController
   ↓
CompanyController / BookstoreController
   ↓
Services (BookstoreService / CompanyService)
   ↓
DAO Layer (AutorDAO, LibroDAO, EmpleadoDAO, etc.)
   ↓
Entities (JPA Models)
   ↓
HibernateUtil
   ↓
MySQL DB
```

### 📚 Capas del Sistema

1. **Controladores**:
    - `MainController`: Coordina la navegación entre módulos
    - `BookstoreController` y `CompanyController`: Gestionan menús específicos

2. **Servicios**:
    - `BookstoreService` y `CompanyService`: Implementan la lógica de negocio
    - Orquestan las operaciones entre entidades y DAOs

3. **DAOs**:
    - Clases DAO para cada entidad (AutorDAO, LibroDAO, EmpleadoDAO, etc.)
    - Encapsulan las operaciones de persistencia y consultas

4. **Entidades**:
    - Clases mapeadas con anotaciones JPA/Hibernate
    - Incluyen métodos helper para mantener la consistencia bidireccional

5. **Utilidades**:
    - `HibernateUtil`: Gestiona la sesión de Hibernate
    - `Constants`: Almacena mensajes y valores constantes

## 🗃 Modelo de Datos

### Requerimiento 1: Sistema de Librerías

#### Entidades y Relaciones:

1. **Autor (1:N con Libro)**
    - Atributos: id, nombre, apellidos, fechaNacimiento
    - Relación: Un autor escribe muchos libros

2. **Editorial (1:N con Libro)**
    - Atributos: id, nombre, dirección
    - Relación: Una editorial publica muchos libros

3. **Libro (N:1 con Autor/Editorial, N:M con Librería)**
    - Atributos: id, título, precio
    - Relaciones: Pertenece a un autor y editorial, está en varias librerías

4. **Librería (N:M con Libro)**
    - Atributos: id, nombre, nombreDueño, dirección
    - Relación: Una librería tiene muchos libros

### Requerimiento 2: Sistema Empresarial

#### Entidades y Relaciones:

1. **Empleado (1:1, N:1, N:M)**
    - Atributos: id, nombre, apellido, email, fechaContratacion, salario
    - Relaciones:
        - Tiene una dirección (1:1)
        - Pertenece a un departamento (N:1)
        - Participa en múltiples proyectos (N:M)

2. **Dirección (1:1 con Empleado)**
    - Atributos: id, calle, ciudad, codigoPostal, país

3. **Departamento (1:N con Empleado)**
    - Atributos: id, nombre, descripción, ubicación

4. **Proyecto (N:M con Empleado)**
    - Atributos: id, nombre, descripción, fechaInicio, fechaFin, presupuesto

## ⚙️ Patrones Implementados

### 🔄 Patrón DAO

Cada entidad tiene su propia clase DAO que implementa operaciones CRUD:

```java
public class EmpleadoDAO {
    private Session session;

    // Método para guardar un empleado
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

    // Métodos findById, findAll, update, delete, etc.
}
```

### 🔄 Patrón Service

Los servicios implementan la lógica de negocio y utilizan los DAOs:

```java
public class BookstoreService {
    private final AutorDAO autorDAO;
    private final EditorialDAO editorialDAO;
    private final LibroDAO libroDAO;
    private final LibreriaDAO libreriaDAO;

    public BookstoreService() {
        this.autorDAO = new AutorDAO();
        this.editorialDAO = new EditorialDAO();
        this.libroDAO = new LibroDAO();
        this.libreriaDAO = new LibreriaDAO();
    }

    // Métodos createBookstoreData, deleteAllData, showAllBooksWithPublisherAndAuthor, etc.
}
```

### 🔄 Patrón Singleton

Implementado en `HibernateUtil` para garantizar una única instancia de `SessionFactory`:

```java
public class HibernateUtil {
    private static SessionFactory sessionFactory;

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

    // Otros métodos para gestionar sesiones
}
```

## 📊 Gestión de Relaciones

### 🔗 Relación Uno a Uno (Empleado - Dirección)

```java
// En Empleado.java
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JoinColumn(name = "direccion_id", unique = true)
private Direccion direccion;

// En Direccion.java
@OneToOne(mappedBy = "direccion")
private Empleado empleado;

// Método helper en Empleado
public void asignarDireccion(Direccion direccion) {
    this.direccion = direccion;
    direccion.setEmpleado(this);
}
```

### 🔗 Relación Uno a Muchos (Departamento - Empleado)

```java
// En Departamento.java
@OneToMany(mappedBy = "departamento")
private List<Empleado> empleados = new ArrayList<>();

// En Empleado.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "departamento_id")
private Departamento departamento;

// Método helper en Empleado
public void asignarDepartamento(Departamento departamento) {
    this.departamento = departamento;
    departamento.getEmpleados().add(this);
}
```

### 🔗 Relación Muchos a Muchos (Empleado - Proyecto)

```java
// En Empleado.java
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
    name = "EMPLEADO_PROYECTO",
    joinColumns = @JoinColumn(name = "empleado_id"),
    inverseJoinColumns = @JoinColumn(name = "proyecto_id")
)
private Set<Proyecto> proyectos = new HashSet<>();

// En Proyecto.java
@ManyToMany(mappedBy = "proyectos")
private Set<Empleado> empleados = new HashSet<>();

// Método helper en Empleado
public void asignarProyecto(Proyecto proyecto) {
    this.proyectos.add(proyecto);
    proyecto.getEmpleados().add(this);
}
```

## ⚡ Validaciones y Manejo de Errores

### 🔍 Validaciones Implementadas

- Verificación de existencia de datos antes de insertar para evitar duplicados
- Control de relaciones (máximo 5 pasajeros por coche en la relación N:M)
- Validación de formatos de datos (campos obligatorios, tipos de datos)

### 🚨 Manejo de Transacciones

Cada operación DAO gestiona sus propias transacciones, soportando incluso transacciones anidadas:

```java
boolean existingTransaction = session.getTransaction().isActive();
Transaction tx = existingTransaction ? session.getTransaction() : session.beginTransaction();

try {
    // Operaciones de base de datos
    if (!existingTransaction) {
        tx.commit();
    }
    return result;
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
```

### 📝 Named Queries

Las entidades utilizan consultas nombradas para operaciones comunes:

```java
@Entity
@Table(name = "EMPLEADOS")
@NamedQuery(name = "Empleado.findAll", query = "FROM Empleado")
@NamedQuery(name = "Empleado.findByNombre", query = "FROM Empleado e WHERE e.nombre = :nombre")
@NamedQuery(name = "Empleado.findByDepartamentoId", query = "FROM Empleado e WHERE e.departamento.id = :departamentoId")
public class Empleado implements Serializable {
    // Atributos y métodos
}
```

## 📊 Resultados y Evidencias

### Menús del Sistema

La aplicación presenta una interfaz de consola con menús intuitivos:

```
===== SISTEMA DE GESTIÓN JPA =====
1. Gestión de Librería (Requerimiento 1)
2. Gestión de Empresa (Requerimiento 2)
3. Salir del sistema
```

#### Menú de Gestión de Librería
```
===== GESTIÓN DE LIBRERÍA =====
1. Crear datos de ejemplo
2. Mostrar libros con editorial y autor
3. Mostrar autores con sus libros
4. Mostrar librerías con sus libros
5. Mostrar libros y sus librerías
6. Eliminar todos los datos
7. Volver al menú principal
```

#### Menú de Gestión de Empresa
```
===== GESTIÓN DE EMPRESA =====
1. Crear datos de ejemplo
2. Mostrar relación Uno a Uno (Empleado-Dirección)
3. Mostrar relación Uno a Muchos (Departamento-Empleados)
4. Mostrar relación Muchos a Muchos (Empleado-Proyecto)
5. Eliminar todos los datos
6. Volver al menú principal
```

### Ejemplos de Consultas Ejecutadas

#### Requerimiento 1: Librerías

1. **Libros con editorial y autor**:
```
===== CONSULTA 1: TODOS LOS LIBROS CON SU EDITORIAL Y AUTOR =====
Libro: La fiesta del chivo - Precio: 26.75€ - Editorial: Penguin Random House - Autor: Mario Vargas Llosa
Libro: La ciudad y los perros - Precio: 24.50€ - Editorial: Penguin Random House - Autor: Mario Vargas Llosa
Libro: El amor en los tiempos del cólera - Precio: 22.50€ - Editorial: Penguin Random House - Autor: Gabriel García Márquez
Libro: Cien años de soledad - Precio: 25.99€ - Editorial: Penguin Random House - Autor: Gabriel García Márquez
Libro: Eva Luna - Precio: 19.99€ - Editorial: Planeta - Autor: Isabel Allende
Libro: La casa de los espíritus - Precio: 21.75€ - Editorial: Planeta - Autor: Isabel Allende
Libro: El otoño del patriarca - Precio: 20.25€ - Editorial: Planeta - Autor: Gabriel García Márquez
Libro: Crónica de una muerte anunciada - Precio: 18.99€ - Editorial: Planeta - Autor: Gabriel García Márquez
```

2. **Autores con sus libros**:
```
===== CONSULTA 2: TODOS LOS AUTORES CON SUS LIBROS ASOCIADOS =====
Autor: Gabriel García Márquez
  - Cien años de soledad (25.99€)
  - El amor en los tiempos del cólera (22.50€)
  - Crónica de una muerte anunciada (18.99€)
  - El otoño del patriarca (20.25€)

Autor: Isabel Allende
  - La casa de los espíritus (21.75€)
  - Eva Luna (19.99€)

Autor: Mario Vargas Llosa
  - La ciudad y los perros (24.50€)
  - La fiesta del chivo (26.75€)
```

#### Requerimiento 2: Empresa

1. **Relación Uno a Uno**:
```
===== RELACIÓN UNO A UNO (Empleado-Dirección) =====
Empleado: Juan Pérez
Dirección: Calle Mayor 10, Madrid, 28001, España

Empleado: María López
Dirección: Avenida Diagonal 100, Barcelona, 08018, España

Empleado: Carlos Gómez
Dirección: Plaza Nueva 5, Sevilla, 41001, España

Empleado: Ana Martínez
Dirección: Gran Vía 30, Madrid, 28013, España
```

2. **Relación Muchos a Muchos**:
```
===== RELACIÓN MUCHOS A MUCHOS (Proyecto-Empleados) =====
--- Proyectos y sus Empleados: ---
Proyecto: Web Corporativa - Rediseño de la web corporativa - Presupuesto: 25000.0€
  Empleados asignados:
  - Juan Pérez - juan.perez@empresa.com
  - Ana Martínez - ana.martinez@empresa.com

Proyecto: App Móvil - Desarrollo de app para clientes - Presupuesto: 45000.0€
  Empleados asignados:
  - María López - maria.lopez@empresa.com
  - Juan Pérez - juan.perez@empresa.com
```

## 💾 Configuración e Instalación

### Archivo de Configuración Hibernate

```xml
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.current_session_context_class">thread</property>
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="connection.url">jdbc:mysql://127.0.0.1:3306/libreriadb</property>
        <property name="connection.username">root</property>
        <property name="connection.password">******</property>
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <mapping class="com.libreria.model.Autor"/>
        <mapping class="com.libreria.model.Editorial"/>
        <mapping class="com.libreria.model.Libro"/>
        <mapping class="com.libreria.model.Libreria"/>
        <mapping class="com.libreria.model.Empleado"/>
        <mapping class="com.libreria.model.Departamento"/>
        <mapping class="com.libreria.model.Proyecto"/>
        <mapping class="com.libreria.model.Direccion"/>
    </session-factory>
</hibernate-configuration>
```

### Pasos de Instalación

1. Clonar el repositorio
2. Configurar la base de datos MySQL ejecutando script.sql
3. Ajustar hibernate.cfg.xml con los datos de conexión
4. Compilar con Maven: `mvn clean install`
5. Ejecutar: `java -jar target/AE_4_JPA-1.0-SNAPSHOT.jar`

## 🎯 Conclusiones

El proyecto implementa satisfactoriamente los dos requerimientos solicitados:

1. Se ha creado un modelo completo para la gestión de librerías con relaciones bidireccionales entre autores, editoriales, libros y librerías.

2. Se ha desarrollado un modelo empresarial que demuestra los tres tipos de relaciones: uno a uno (Empleado-Dirección), uno a muchos (Departamento-Empleados) y muchos a muchos (Empleado-Proyecto).

Las decisiones técnicas tomadas, como el uso de patrones de diseño, la gestión cuidadosa de transacciones, la implementación de métodos helper para relaciones bidireccionales y el uso de consultas nombradas, garantizan un sistema robusto y mantenible.

El uso de JPA/Hibernate ha permitido crear un mapeo objeto-relacional efectivo, demostrando las capacidades de esta tecnología para gestionar modelos de datos complejos con distintos tipos de relaciones, asegurando la integridad referencial y facilitando las consultas entre entidades relacionadas.

---

## ✒️ Autor
* **Oriol Macias** - *Desarrollador*