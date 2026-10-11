package cl.biblioteca.dao;
import cl.biblioteca.model.Libro;
import java.util.List;

public interface LibroDAO {
    /**
     * Reporte: préstamos que aún no han sido devueltos.
     *
     * @return filas del reporte
     */
    boolean create(Libro libro);
    /**
     * Actualiza un registro existente.
     *
     * @param libro objeto con los datos nuevos
     * @return true si se actualizó correctamente
     */
    boolean update(Libro libro);
    /**
     * Elimina un registro por su id.
     *
     * @param idLibro id del registro a eliminar
     * @return true si se eliminó correctamente
     */
    boolean delete(int idLibro);
    /**
     * Obtiene todos los registros.
     *
     * @return lista con todos los registros
     */
    List<Libro> readAll();
    /**
     * Resta una unidad al stock del libro.
     *
     * @param idLibro id del libro
     * @return true si había stock y se descontó
     */
    boolean descontarStock(int idLibro);
    /**
     * Suma una unidad al stock del libro.
     *
     * @param idLibro id del libro
     * @return true si se actualizó correctamente
     */
    boolean aumentarStock(int idLibro);
}
