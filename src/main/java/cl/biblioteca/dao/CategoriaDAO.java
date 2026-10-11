package cl.biblioteca.dao;

import cl.biblioteca.model.Categoria;

import java.util.List;

public interface CategoriaDAO {
    /**
     * Inserta un registro nuevo.
     *
     * @param categoria objeto a guardar
     * @return true si se guardó correctamente
     */
    boolean create(Categoria categoria);
    /**
     * Actualiza un registro existente.
     *
     * @param categoria objeto con los datos nuevos
     * @return true si se actualizó correctamente
     */
    boolean update(Categoria categoria);
    /**
     * Elimina un registro por su id.
     *
     * @param idCategoria id del registro a eliminar
     * @return true si se eliminó correctamente
     */
    boolean delete(int idCategoria);
    /**
     * Obtiene todos los registros.
     *
     * @return lista con todos los registros
     */
    List<Categoria> readAll();
}
