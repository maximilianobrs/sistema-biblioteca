package cl.biblioteca.controller;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.dao.impl.LibroDAOImpl;
import cl.biblioteca.dao.impl.PrestamoDAOImpl;
import cl.biblioteca.model.GestorPrestamo;
import cl.biblioteca.model.Prestamo;

import java.time.LocalDate;
import java.util.List;

public class PrestamoController {

    private PrestamoDAO prestamoDAO = new PrestamoDAOImpl();
    private static GestorPrestamo gestorPrestamo = new GestorPrestamo();

    /**
     * Registra un nuevo préstamo.
     *
     * @param idEstudiante id del estudiante
     * @param idLibro id del libro
     * @return true si se registró
     */
    public boolean crear(int idEstudiante, int idLibro){
        return gestorPrestamo.registrarPrestamo(idEstudiante, idLibro);
    }

    /**
     * Registra la devolución de un préstamo (repone stock).
     *
     * @param idPrestamo id del préstamo
     * @return true si se registró la devolución
     */
    public boolean devolver(int idPrestamo){
        return gestorPrestamo.registrarDevolucion(idPrestamo);
    }

    /**
     * Actualiza manualmente los datos de un préstamo.
     *
     * @param idPrestamo id del préstamo
     * @param idEstudiante id del estudiante
     * @param idLibro id del libro
     * @param fechaPrestamo fecha del préstamo
     * @param fechaDevolucion fecha límite de devolución
     * @param devuelto true si ya fue devuelto
     * @return true si se actualizó
     */
    public boolean actualizar(int idPrestamo, int idEstudiante, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto){
        Prestamo prestamo = new Prestamo(idPrestamo, idEstudiante, idLibro,
                fechaPrestamo, fechaDevolucion, devuelto);
        return prestamoDAO.update(prestamo);
    }

    /**
     * Elimina un préstamo.
     *
     * @param idPrestamo id del préstamo
     * @return true si se eliminó
     */
    public boolean eliminar(int idPrestamo){
        return prestamoDAO.delete(idPrestamo);
    }

    /**
     * Lista todos los préstamos.
     *
     * @return lista de préstamos
     */
    public List<Prestamo> listarTodo(){
        return prestamoDAO.readAll();
    }

    /**
     * Reporte de los libros más prestados.
     *
     * @return filas del reporte
     */
    public List<Object[]> reporteLibrosMasPrestados(){
        return prestamoDAO.librosMasPrestados();
    }

    /**
     * Reporte del historial de préstamos de un estudiante.
     *
     * @param idEstudiante id del estudiante
     * @return filas del reporte
     */
    public List<Object[]> reporteHistorialEstudiante(int idEstudiante){
        return prestamoDAO.historialEstudiante(idEstudiante);
    }

    /**
     * Reporte de los préstamos activos (no devueltos).
     *
     * @return filas del reporte
     */
    public List<Object[]> reportePrestamosActivos(){
        return prestamoDAO.prestamosActivos();
    }
}
