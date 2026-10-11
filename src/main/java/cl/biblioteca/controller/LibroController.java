package cl.biblioteca.controller;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.impl.LibroDAOImpl;
import cl.biblioteca.model.Libro;

import java.util.List;

public class LibroController {
    private LibroDAO libroDAO = new LibroDAOImpl();

    /**
     * Crea un libro.
     *
     * @param titulo título
     * @param autor autor
     * @param isbn código ISBN
     * @param editorial editorial
     * @param stock ejemplares disponibles
     * @param idCategoria id de la categoría
     * @return true si se creó; false si los datos no son válidos o falló
     */
    public boolean crear(String titulo, String autor, String isbn, String editorial, int stock, int idCategoria){
        if (titulo.isEmpty() || stock < 0) {
            return false;
        }
        Libro libro = new Libro(titulo, autor, isbn, editorial, stock, idCategoria);
        return libroDAO.create(libro);
    }

    /**
     * Actualiza los datos de un libro.
     *
     * @param idLibro id del libro
     * @param titulo título
     * @param autor autor
     * @param isbn código ISBN
     * @param editorial editorial
     * @param stock ejemplares disponibles
     * @param idCategoria id de la categoría
     * @return true si se actualizó
     */
    public boolean actualizar(int idLibro, String titulo, String autor, String isbn, String editorial, int stock, int idCategoria){
        if (titulo.isEmpty() || stock < 0) {
            return false;
        }
        Libro libro = new Libro(idLibro, titulo, autor, isbn, editorial, stock, idCategoria);
        return libroDAO.update(libro);
    }

    /**
     * Elimina un libro.
     *
     * @param idLibro id del libro
     * @return true si se eliminó
     */
    public boolean eliminar(int idLibro){
        return libroDAO.delete(idLibro);
    }

    /**
     * Lista todos los libros.
     *
     * @return lista de libros
     */
    public List<Libro> listarTodo(){
        return libroDAO.readAll();
    }
}
