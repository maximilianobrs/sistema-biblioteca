# Sistema de Gestión de Biblioteca Escolar

Aplicación de escritorio en Java para controlar el registro, préstamo y devolución de libros en una biblioteca escolar. Proyecto de la Evaluación Final Transversal de Desarrollo Orientado a Objetos II.

## Funcionalidades

- **Login** con roles: *bibliotecario* (gestión total) y *estudiante* (solo préstamos, devoluciones y consulta).
- **Libros**: registrar, modificar, eliminar y listar, con categoría, autor, editorial, ISBN y stock.
- **Estudiantes**: registrar y administrar estudiantes.
- **Préstamos y devoluciones**: la fecha de devolución se calcula automáticamente (7 días) y el stock se actualiza solo.
- **Reportes**: libros más prestados, historial por estudiante y préstamos activos.

## Patrones y buenas prácticas

- **MVC**: paquetes `model`, `view` y `controller` separados.
- **DAO**: una interfaz y una implementación por entidad (`dao` y `dao/impl`).
- **Singleton**: `ConexionDB` mantiene una única conexión a la base de datos.
- **Hilos**: el registro de un préstamo se ejecuta en un `Thread` aparte para no congelar la interfaz.
- **Sincronización**: `synchronized` en el registro de préstamos y en los métodos que modifican el stock, para evitar condiciones de carrera.

## Estructura del proyecto

```
src/main/java/cl/biblioteca/
├── main/         Main (punto de entrada)
├── model/        Persona, Estudiante, Usuario, Libro, Categoria, Prestamo, GestorPrestamo
├── view/         Ventanas (login, principal, libros, estudiantes, préstamos, reportes)
├── controller/   Controladores de cada módulo
├── dao/          Interfaces DAO
│   └── impl/     Implementaciones JDBC
└── utils/        ConexionDB (Singleton)
```

## Cómo ejecutarlo

1. Crear la base de datos `biblioteca` en MySQL.
2. Ejecutar primero el script de creación de tablas y luego el de poblado de datos.
3. Revisar usuario y contraseña de MySQL en `utils/ConexionDB.java` (por defecto `root` / `12345`, puerto `3306`).
4. Abrir el proyecto en IntelliJ IDEA, esperar a que Maven descargue las dependencias y ejecutar la clase `Main`.
5. Iniciar sesión con un correo registrado en la tabla de usuarios.

## Autor

Maximiliano Briones