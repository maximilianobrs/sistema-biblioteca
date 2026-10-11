package cl.biblioteca.controller;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.dao.impl.UsuarioDAOImpl;
import cl.biblioteca.model.Usuario;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public boolean crear(String nombre, String rut, String correo, String contrasena, String rol){
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        Usuario usuario = new Usuario(correo, rut, nombre, rol);
        usuario.setContrasena(contrasena);
        return usuarioDAO.create(usuario);
    }

    public boolean actualizar(int id, String nombre, String rut, String correo, String contrasena, String rol){
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            return false;
        }
        Usuario usuario = new Usuario(id, nombre, rut, correo, contrasena, rol);
        return usuarioDAO.update(usuario);
    }

    public boolean eliminar(int idUsuario){
        return usuarioDAO.delete(idUsuario);
    }

    public List<Usuario> listarTodo(){
        return usuarioDAO.readAll();
    }

    public Usuario login(String correo){
        if (correo.isEmpty()) {
            return null;
        }
        return usuarioDAO.login(correo);
    }
}
