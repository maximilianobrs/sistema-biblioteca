package cl.biblioteca.controller;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.impl.LibroDAOImpl;
import cl.biblioteca.model.Libro;

import java.util.List;

public class LibroController {
    private LibroDAO libroDAO = new LibroDAOImpl();

    public boolean crear(String titulo, String autor, String isbn, String editorial, int stock, int idCategoria){
        if (titulo.isEmpty() || stock < 0) {
            return false;
        }
        Libro libro = new Libro(titulo, autor, isbn, editorial, stock, idCategoria);
        return libroDAO.create(libro);
    }

    public boolean actualizar(int idLibro, String titulo, String autor, String isbn, String editorial, int stock, int idCategoria){
        if (titulo.isEmpty() || stock < 0) {
            return false;
        }
        Libro libro = new Libro(idLibro, titulo, autor, isbn, editorial, stock, idCategoria);
        return libroDAO.update(libro);
    }

    public boolean eliminar(int idLibro){
        return libroDAO.delete(idLibro);
    }

    public List<Libro> listarTodo(){
        return libroDAO.readAll();
    }

    public boolean descontarStock(int idLibro){
        return libroDAO.descontarStock(idLibro);
    }

    public boolean aumentarStock(int idLibro){
        return libroDAO.aumentarStock(idLibro);
    }
}
