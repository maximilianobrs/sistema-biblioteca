package cl.biblioteca.controller;

import cl.biblioteca.dao.CategoriaDAO;
import cl.biblioteca.dao.impl.CategoriaDAOImpl;
import cl.biblioteca.model.Categoria;

import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    /**
     * Crea una categoría.
     *
     * @param nombreCategoria nombre
     * @return true si se creó; false si los datos no son válidos o falló
     */
    public boolean crear(String nombreCategoria){
        if (nombreCategoria.isEmpty()) {
            return false;
        }

        Categoria categoria = new Categoria(nombreCategoria);

        return categoriaDAO.create(categoria);
    }

    /**
     * Actualiza el nombre de una categoría.
     *
     * @param idCategoria id de la categoría
     * @param nombreCategoria nuevo nombre
     * @return true si se actualizó
     */
    public boolean actualizar(int idCategoria, String nombreCategoria){
        if (nombreCategoria.isEmpty()) {
            return false;
        }
        Categoria categoria = new Categoria(idCategoria, nombreCategoria);
        return categoriaDAO.update(categoria);
    }

    /**
     * Elimina una categoría.
     *
     * @param idCategoria id de la categoría
     * @return true si se eliminó
     */
    public boolean eliminar(int idCategoria){
        return categoriaDAO.delete(idCategoria);
    }

    /**
     * Lista todas las categorías.
     *
     * @return lista de categorías
     */
    public List<Categoria> listarTodo(){
        return categoriaDAO.readAll();
    }
}
