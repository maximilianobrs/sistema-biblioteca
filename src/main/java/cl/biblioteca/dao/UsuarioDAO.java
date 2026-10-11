package cl.biblioteca.dao;

import cl.biblioteca.model.Usuario;

import java.util.List;

public interface UsuarioDAO {
    boolean create(Usuario usuario);
    boolean update(Usuario usuario);
    boolean delete(int idUsuario);
    List<Usuario> readAll();
    Usuario login(String correo);
}
