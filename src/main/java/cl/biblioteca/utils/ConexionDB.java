package cl.biblioteca.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Maneja la conexión a la base de datos MySQL.
 * Usa el patrón Singleton: solo existe una instancia compartida en todo el sistema.
 */
public class ConexionDB {

    /**
     * Única instancia de la clase.
     */
    private static ConexionDB instanciaUnica;

    /**
     * Conexión JDBC activa.
     */
    private Connection conexion;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    /**
     * Constructor privado: abre la conexión con la base de datos.
     */
    private ConexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion correctamente con la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Entrega la instancia única; si la conexión se cerró, crea una nueva.
     *
     * @return instancia de ConexionDB
     */
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

    /**
     * @return conexión JDBC puede ser null si fallo al conectar.
     */
    public Connection getConexion() {
        return this.conexion;
    }
}
