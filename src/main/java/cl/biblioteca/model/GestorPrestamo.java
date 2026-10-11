package cl.biblioteca.model;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.dao.impl.LibroDAOImpl;
import cl.biblioteca.dao.impl.PrestamoDAOImpl;

import java.time.LocalDate;

public class GestorPrestamo {
    private LibroDAO libroDAO = new LibroDAOImpl();
    private PrestamoDAO prestamoDAO = new PrestamoDAOImpl();

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
