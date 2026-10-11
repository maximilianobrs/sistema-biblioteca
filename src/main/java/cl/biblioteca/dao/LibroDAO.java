package cl.biblioteca.dao;
import cl.biblioteca.model.Libro;
import java.util.List;

public interface LibroDAO {
    boolean create(Libro libro);
    boolean update(Libro libro);
    boolean delete(int idLibro);
    List<Libro> readAll();
    boolean descontarStock(int idLibro);
    boolean aumentarStock(int idLibro);
}
