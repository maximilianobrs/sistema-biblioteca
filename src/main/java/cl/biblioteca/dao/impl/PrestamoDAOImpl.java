package cl.biblioteca.dao.impl;

import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.model.Prestamo;
import cl.biblioteca.utils.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {

    @Override
    public boolean create(Prestamo prestamo){
        String sql = "INSERT INTO prestamos(id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdEstudiante());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            stmt.setBoolean(5, prestamo.isDevuelto());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD guardar: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar el prestamo.");
        }
    }

    @Override
    public boolean update(Prestamo prestamo){
        String sql = "UPDATE prestamos SET " +
                "id_estudiante = ?, " +
                "id_libro = ?, " +
                "fecha_prestamo = ?, " +
                "fecha_devolucion = ?, " +
                "devuelto = ? " +
                "WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prestamo.getIdEstudiante());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            stmt.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            stmt.setBoolean(5, prestamo.isDevuelto());
            stmt.setInt(6, prestamo.getIdPrestamo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD actualizar: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el prestamo.");
        }
    }

    @Override
    public boolean delete(int idPrestamo){
        String sql = "DELETE FROM prestamos WHERE id = ?";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPrestamo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error BD eliminar: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el prestamo.");
        }
    }

    @Override
    public List<Prestamo> readAll(){
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT id , id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto FROM prestamos";

        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idPrestamo = rs.getInt("id");
                int idEstudiante = rs.getInt("id_estudiante");
                int idLibro = rs.getInt("id_libro");
                LocalDate fechaPrestamo = rs.getDate("fecha_prestamo").toLocalDate();
                LocalDate fechaDevolucion = rs.getDate("fecha_devolucion").toLocalDate();
                boolean devuelto = rs.getBoolean("devuelto");

                Prestamo prestamo = new Prestamo(idPrestamo, idEstudiante, idLibro,
                        fechaPrestamo, fechaDevolucion, devuelto);

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println("Error BD listar: " + e.getMessage());
            throw new RuntimeException("No se pudo obtener la lista de prestamos.");
        }

        return prestamos;
    }

    @Override
    public List<Object[]> librosMasPrestados(){
        String sql = "SELECT l.titulo, COUNT(*) AS total FROM prestamos p " +
                "JOIN libros l ON p.id_libro = l.id " +
                "GROUP BY l.id, l.titulo ORDER BY total DESC";

        List<Object[]> filas = new ArrayList<>();
        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                filas.add(new Object[]{rs.getString("titulo"), rs.getInt("total")});
            }

        } catch (SQLException e) {
            System.out.println("Error BD reporte: " + e.getMessage());
            throw new RuntimeException("No se pudo generar el reporte.");
        }

        return filas;
    }

    @Override
    public List<Object[]> historialEstudiante(int idEstudiante){
        String sql = "SELECT l.titulo, p.fecha_prestamo, p.fecha_devolucion, p.devuelto FROM prestamos p " +
                "JOIN libros l ON p.id_libro = l.id " +
                "WHERE p.id_estudiante = ? ORDER BY p.fecha_prestamo DESC";

        List<Object[]> filas = new ArrayList<>();
        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstudiante);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    filas.add(new Object[]{
                            rs.getString("titulo"),
                            rs.getDate("fecha_prestamo"),
                            rs.getDate("fecha_devolucion"),
                            calcularEstado(rs.getDate("fecha_devolucion"), rs.getBoolean("devuelto"))
                    });
                }
            }

        } catch (SQLException e) {
            System.out.println("Error BD reporte: " + e.getMessage());
            throw new RuntimeException("No se pudo generar el reporte.");
        }

        return filas;
    }

    @Override
    public List<Object[]> prestamosActivos(){
        String sql = "SELECT e.nombre, l.titulo, p.fecha_prestamo, p.fecha_devolucion FROM prestamos p " +
                "JOIN libros l ON p.id_libro = l.id " +
                "JOIN estudiantes e ON p.id_estudiante = e.id " +
                "WHERE p.devuelto = 0 ORDER BY p.fecha_devolucion";

        List<Object[]> filas = new ArrayList<>();
        Connection conn = ConexionDB.getInstancia().getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                filas.add(new Object[]{
                        rs.getString("nombre"),
                        rs.getString("titulo"),
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion"),
                        calcularEstado(rs.getDate("fecha_devolucion"), false)
                });
            }

        } catch (SQLException e) {
            System.out.println("Error BD reporte: " + e.getMessage());
            throw new RuntimeException("No se pudo generar el reporte.");
        }

        return filas;
    }

    private String calcularEstado(Date fechaDevolucion, boolean devuelto){
        if (devuelto) {
            return "Devuelto";
        }
        if (fechaDevolucion.toLocalDate().isBefore(LocalDate.now())) {
            return "ATRASADO";
        }
        return "En plazo";
    }
}
