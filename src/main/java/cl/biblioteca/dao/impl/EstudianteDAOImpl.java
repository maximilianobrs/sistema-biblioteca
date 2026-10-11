package cl.biblioteca.dao.impl;

import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.model.Estudiante;
import cl.biblioteca.utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    @Override
    public boolean create(Estudiante estudiante){
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD guardar: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el estudiante.");
        }
    }
    @Override
    public boolean update(Estudiante estudiante){
        String sql = "UPDATE estudiantes SET " +
                "nombre = ?, " +
                "rut = ?, " +
                "curso = ?, " +
                "correo = ? " +
                "WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getRut());
            stmt.setString(3, estudiante.getCurso());
            stmt.setString(4, estudiante.getCorreo());
            stmt.setInt(5, estudiante.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el estudiante.");
        }
    }
    @Override
    public boolean delete(int idEstudiante){
        String sql = "DELETE FROM estudiantes WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el estudiante.");
        }
    }

    @Override
    public List<Estudiante> readAll(){
        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT id, nombre, rut, curso, correo FROM estudiantes";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idEstudiante = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String rut = rs.getString("rut");
                String curso = rs.getString("curso");
                String correo = rs.getString("correo");

                Estudiante estudiante = new Estudiante(idEstudiante, nombre, rut, curso, correo);

                estudiantes.add(estudiante);
            }

        } catch (SQLException e) {
            System.out.println("Error BD listar: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de estudiantes.");
        }

        return estudiantes;
    }
}
