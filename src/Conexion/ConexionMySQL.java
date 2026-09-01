package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL =
            "jdbc:mysql://localhost:3306/wcdonalds_db";

    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123456789";

    public static Connection conectar() {

        Connection conexion = null;

        try {

            conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            System.out.println("Conexion exitosa a MySQL");

        } catch (SQLException e) {

            System.out.println("Error al conectar a MySQL:");
            System.out.println(e.getMessage());
        }

        return conexion;
    }
}