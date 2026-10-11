package cl.biblioteca.controller;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.dao.impl.UsuarioDAOImpl;
import cl.biblioteca.model.Usuario;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    /**
     * Crea un usuario.
     *
     * @param nombre nombre completo
     * @param rut RUT
     * @param correo correo electrónico
     * @param contrasena contraseña
     * @param rol rol del usuario
     * @return true si se creó; false si los datos no son válidos o falló
     */
    public boolean crear(String nombre, String rut, String correo, String contrasena, String rol){
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        Usuario usuario = new Usuario(correo, rut, nombre, rol);
        usuario.setContrasena(contrasena);
        return usuarioDAO.create(usuario);
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param id id del usuario
     * @param nombre nombre completo
     * @param rut RUT
     * @param correo correo electrónico
     * @param contrasena contraseña
     * @param rol rol del usuario
     * @return true si se actualizó
     */
    public boolean actualizar(int id, String nombre, String rut, String correo, String contrasena, String rol){
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        Usuario usuario = new Usuario(id, nombre, rut, correo, contrasena, rol);
        return usuarioDAO.update(usuario);
    }

    /**
     * Elimina un usuario.
     *
     * @param idUsuario id del usuario
     * @return true si se eliminó
     */
    public boolean eliminar(int idUsuario){
        return usuarioDAO.delete(idUsuario);
    }

    /**
     * Lista todos los usuarios.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarTodo(){
        return usuarioDAO.readAll();
    }

    /**
     * Busca al usuario que quiere iniciar sesión.
     *
     * @param correo correo ingresado
     * @return si el correo está vacío o no existe
     */
    public Usuario login(String correo){
        if (correo.isEmpty()) {
            return null;
        }
        return usuarioDAO.login(correo);
    }
}
