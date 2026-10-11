package cl.biblioteca.dao;

import cl.biblioteca.model.Prestamo;

import java.util.List;

public interface PrestamoDAO {
    /**
     * Inserta un registro nuevo.
     *
     * @param prestamo objeto a guardar
     * @return true si se guardó correctamente
     */
    boolean create(Prestamo prestamo);
    /**
     * Actualiza un registro existente.
     *
     * @param prestamo objeto con los datos nuevos
     * @return true si se actualizó correctamente
     */
    boolean update(Prestamo prestamo);    /**
     * Elimina un registro por su id.
     *
     * @param idPrestamo id del registro a eliminar
     * @return true si se eliminó correctamente
     */
    boolean delete(int idPrestamo);
    /**
     * Obtiene todos los registros.
     *
     * @return lista con todos los registros
     */
    List<Prestamo> readAll();
    /**
     * Reporte: libros ordenados por cantidad de préstamos.
     *
     * @return filas del reporte
     */
    List<Object[]> librosMasPrestados();
    /**
     * Reporte: historial de préstamos de un estudiante.
     *
     * @param idEstudiante id del estudiante
     * @return filas del reporte
     */
    List<Object[]> historialEstudiante(int idEstudiante);
    /**
     * Reporte: préstamos que aún no han sido devueltos.
     *
     * @return filas del reporte
     */
    List<Object[]> prestamosActivos();
}
