package cl.biblioteca.dao;

import cl.biblioteca.model.Categoria;

import java.util.List;

public interface CategoriaDAO {
    boolean create(Categoria categoria);
    boolean update(Categoria categoria);
    boolean delete(int idCategoria);
    List<Categoria> readAll();
}
