package cl.biblioteca.dao;

import cl.biblioteca.model.Prestamo;

import java.util.List;

public interface PrestamoDAO {
    boolean create(Prestamo prestamo);
    boolean update(Prestamo prestamo);
    boolean delete(int idPrestamo);
    List<Prestamo> readAll();
    List<Object[]> librosMasPrestados();
    List<Object[]> historialEstudiante(int idEstudiante);
    List<Object[]> prestamosActivos();
}
