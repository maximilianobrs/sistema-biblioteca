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

    public boolean crear(int idEstudiante, int idLibro){
        return gestorPrestamo.registrarPrestamo(idEstudiante, idLibro);
    }

    public boolean devolver(int idPrestamo){
        return gestorPrestamo.registrarDevolucion(idPrestamo);
    }

    public boolean actualizar(int idPrestamo, int idEstudiante, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto){
        Prestamo prestamo = new Prestamo(idPrestamo, idEstudiante, idLibro,
                fechaPrestamo, fechaDevolucion, devuelto);
        return prestamoDAO.update(prestamo);
    }

    public boolean eliminar(int idPrestamo){
        return prestamoDAO.delete(idPrestamo);
    }

    public List<Prestamo> listarTodo(){
        return prestamoDAO.readAll();
    }

    public List<Object[]> reporteLibrosMasPrestados(){
        return prestamoDAO.librosMasPrestados();
    }

    public List<Object[]> reporteHistorialEstudiante(int idEstudiante){
        return prestamoDAO.historialEstudiante(idEstudiante);
    }

    public List<Object[]> reportePrestamosActivos(){
        return prestamoDAO.prestamosActivos();
    }
}
