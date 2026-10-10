package cl.biblioteca.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static ConexionDB instanciaUnica;
    private Connection conexion;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion correctamente con la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static synchronized ConexionDB getInstancia() {

        if (instanciaUnica == null) {

            instanciaUnica = new ConexionDB();

        } else {
            try {
                if (instanciaUnica.getConexion() == null || instanciaUnica.getConexion().isClosed()) {
                    instanciaUnica = new ConexionDB();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instanciaUnica;
    }

    public Connection getConexion() {
        return this.conexion;
    }
}
