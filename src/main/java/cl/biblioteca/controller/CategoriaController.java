package cl.biblioteca.controller;

import cl.biblioteca.dao.CategoriaDAO;
import cl.biblioteca.dao.impl.CategoriaDAOImpl;
import cl.biblioteca.model.Categoria;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    public boolean crear(String nombreCategoria){
        if (nombreCategoria.isEmpty()) {
            return false;
        }

        Categoria categoria = new Categoria(nombreCategoria);

        return categoriaDAO.create(categoria);
    }

    public boolean actualizar(int idCategoria, String nombreCategoria){
        if (nombreCategoria.isEmpty()) {
            return false;
        }
        Categoria categoria = new Categoria(idCategoria, nombreCategoria);
        return categoriaDAO.update(categoria);
    }

    public boolean eliminar(int idCategoria){
        return categoriaDAO.delete(idCategoria);
    }

    public List<Categoria> listarTodo(){
        return categoriaDAO.readAll();
    }
}
