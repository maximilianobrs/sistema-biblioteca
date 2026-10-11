package cl.biblioteca.model;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.dao.impl.LibroDAOImpl;
import cl.biblioteca.dao.impl.PrestamoDAOImpl;

import java.time.LocalDate;

/**
 * Gestiona las reglas de negocio de los préstamos y devoluciones.
 * Mantiene coherente el stock de los libros con los préstamos registrados.
 */
public class GestorPrestamo {
    private LibroDAO libroDAO = new LibroDAOImpl();
    private PrestamoDAO prestamoDAO = new PrestamoDAOImpl();

    /**
     * Registra un préstamo: descuenta una unidad de stock y guarda el préstamo
     * con devolución a 7 días. Si el guardado falla, devuelve el stock.
     * @param idEstudiante id del estudiante que pide el libro
     * @param idLibro id del libro a prestar
     * @return true si el préstamo quedó registrado; false si no hay stock o falló
     */
    public synchronized boolean registrarPrestamo(int idEstudiante, int idLibro) {
        if (!libroDAO.descontarStock(idLibro)) {
            return false;
        }

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(7);
        Prestamo prestamo = new Prestamo(idEstudiante, idLibro, fechaPrestamo, fechaDevolucion);

        try {
            boolean guardado = prestamoDAO.create(prestamo);
            if (!guardado) {
                libroDAO.aumentarStock(idLibro);
            }
            return guardado;
        } catch (RuntimeException ex) {
            libroDAO.aumentarStock(idLibro);
            throw ex;
        }
    }

    /**
     * Registra la devolución de un préstamo y repone el stock del libro.
     * @param idPrestamo id del préstamo a cerrar
     * @return true si se actualizó; false si no existe o ya estaba devuelto
     */
    public synchronized boolean registrarDevolucion(int idPrestamo) {
        for (Prestamo prestamo : prestamoDAO.readAll()) {
            if (prestamo.getIdPrestamo() == idPrestamo && !prestamo.isDevuelto()) {
                prestamo.setDevuelto(true);
                boolean actualizado = prestamoDAO.update(prestamo);

                if (actualizado) {
                    libroDAO.aumentarStock(prestamo.getIdLibro());
                }
                return actualizado;
            }
        }
        return false;
    }
}
