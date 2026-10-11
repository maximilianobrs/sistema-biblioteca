package cl.biblioteca.dao.impl;

import cl.biblioteca.dao.CategoriaDAO;
import cl.biblioteca.model.Categoria;
import cl.biblioteca.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public boolean create(Categoria categoria){
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNombreCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD guardar: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar la categoria.");
        }
    }

    @Override
    public boolean update(Categoria categoria){
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setInt(2, categoria.getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar la categoria.");
        }
    }

    @Override
    public boolean delete(int idCategoria){
        String sql = "DELETE FROM categorias WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idCategoria);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar la categoria.");
        }
    }
    @Override
    public List<Categoria> readAll(){
        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id, nombre FROM categorias";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idCategoria = rs.getInt("id");
                String nombreCategoria = rs.getString("nombre");

                Categoria categoria = new Categoria(idCategoria,nombreCategoria);

                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.out.println("Error BD listar: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de categorias.");
        }

        return categorias;
    }
}
