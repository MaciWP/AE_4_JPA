-- Check number of relations in the junction table
USE libreriadb;

-- Count records
SELECT COUNT(*) AS total_libreria_libro FROM LIBRERIA_LIBRO;

-- Show actual relations
SELECT
    ll.libreria_id,
    lib.nombre AS libreria_nombre,
    ll.libro_id,
    l.titulo AS libro_titulo
FROM
    LIBRERIA_LIBRO ll
JOIN
    LIBRERIAS lib ON ll.libreria_id = lib.id
JOIN
    LIBROS l ON ll.libro_id = l.id
ORDER BY
    lib.nombre, l.titulo;