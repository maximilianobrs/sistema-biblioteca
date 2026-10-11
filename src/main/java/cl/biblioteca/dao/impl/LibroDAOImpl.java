package cl.biblioteca.dao.impl;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.model.Libro;
import cl.biblioteca.utils.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public boolean create(Libro libro){
        String sql = "INSERT INTO libros(titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD guardar: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el libro.");
        }
    }
    @Override
    public boolean update(Libro libro){
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, id_categoria = ? WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());
            stmt.setString(4, libro.getEditorial());
            stmt.setInt(5, libro.getStock());
            stmt.setInt(6, libro.getIdCategoria());
            stmt.setInt(7, libro.getIdLibro());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el libro.");
        }
    }

    @Override
    public boolean delete(int idLibro){
        String sql = "DELETE FROM libros WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLibro);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el libro.");
        }
    }

    @Override
    public List<Libro> readAll(){
        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT id, titulo, autor, isbn, editorial, stock, id_categoria FROM libros";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idLibro = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String isbn = rs.getString("isbn");
                String editorial = rs.getString("editorial");
                int stock = rs.getInt("stock");
                int idCategoria = rs.getInt("id_categoria");

                Libro libro = new Libro(idLibro, titulo, autor, isbn, editorial, stock, idCategoria);

                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println("Error BD listar: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de libros.");
        }

        return libros;
    }

    @Override
    public synchronized boolean descontarStock(int idLibro){
        String sql = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLibro);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD stock: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el stock.");
        }
    }

    @Override
    public synchronized boolean aumentarStock(int idLibro){
        String sql = "UPDATE libros SET stock = stock + 1 WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLibro);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD stock: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el stock.");
        }
    }
}
