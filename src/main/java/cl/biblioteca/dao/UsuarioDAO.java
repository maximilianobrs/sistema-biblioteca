package cl.biblioteca.dao;

import cl.biblioteca.model.Usuario;

import java.util.List;

public interface UsuarioDAO {
    /**
     * Inserta un registro nuevo.
     *
     * @param usuario objeto a guardar
     * @return true si se guardó correctamente
     */
    boolean create(Usuario usuario);
    /**
     * Actualiza un registro existente.
     *
     * @param usuario objeto con los datos nuevos
     * @return true si se actualizó correctamente
     */
    boolean update(Usuario usuario);
    /**
     * Elimina un registro por su id.
     *
     * @param idUsuario id del registro a eliminar
     * @return true si se eliminó correctamente
     */
    boolean delete(int idUsuario);
    /**
     * Obtiene todos los registros.
     *
     * @return lista con todos los registros
     */
    List<Usuario> readAll();
    /**
     * Busca un usuario por correo para validar el inicio de sesión.
     *
     * @param correo correo del usuario
     * @return el usuario, o null si no existe
     */
    Usuario login(String correo);
}
