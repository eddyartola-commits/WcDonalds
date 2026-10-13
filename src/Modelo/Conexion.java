package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    // Cambia "wcdonalds" si tu base de datos en MySQL tiene otro nombre
  private static final String URL = "jdbc:mysql://localhost:3306/wcdonalds_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789"; // Pon tu contraseña de MySQL si tienes una

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar Driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        }
        return con;
    }
}