package cl.biblioteca.dao.impl;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.model.Usuario;
import cl.biblioteca.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public boolean create(Usuario usuario){
        String sql = "INSERT INTO usuarios(nombre, rut, correo, `contraseña`, rol) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getRol());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD guardar: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el usuario.");
        }
    }

    @Override
    public boolean update(Usuario usuario){
        String sql = "UPDATE usuarios SET " +
                "nombre = ?, " +
                "rut = ?, " +
                "correo = ?, " +
                "`contraseña` = ?, " +
                "rol = ? " +
                "WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRut());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el usuario.");
        }
    }

    @Override
    public boolean delete(int idUsuario){
        String sql = "DELETE FROM usuarios WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el usuario.");
        }
    }
    @Override
    public List<Usuario> readAll(){
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nombre, rut, correo, `contraseña`, rol FROM usuarios";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idUsuario = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String rut = rs.getString("rut");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contraseña");
                String rol = rs.getString("rol");

                Usuario usuario = new Usuario(idUsuario, nombre, rut, correo, contrasena, rol);

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error BD listar: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de usuarios.");
        }

        return usuarios;
    }

    @Override
    public Usuario login(String correo){
        String sql = "SELECT id, nombre, rut, correo, `contraseña`, rol FROM usuarios WHERE correo = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("rut"),
                            rs.getString("correo"), rs.getString("contraseña"), rs.getString("rol"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error BD login: " + e.getMessage());
            throw new RuntimeException("No se pudo validar el usuario.");
        }

        return null;
    }
}
