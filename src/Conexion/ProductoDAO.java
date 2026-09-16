package Conexion;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> obtenerProductosPorCategoria(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        // Ajusta los nombres de las columnas según tu tabla en MySQL
        String sql = "SELECT * FROM productos WHERE id_categoria = ?";

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagenPath(rs.getString("imagen_path"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setDisponible(rs.getBoolean("disponible"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }

        return lista;
    }
    
    public java.util.List<Modelo.Producto> buscarProductosPorNombre(String texto) {
    java.util.List<Modelo.Producto> lista = new java.util.ArrayList<>();
    String sql = "SELECT * FROM productos WHERE nombre LIKE ? AND disponible = 1";

    try (java.sql.Connection con = Conexion.ConexionMySQL.conectar();
         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, "%" + texto + "%");
        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Modelo.Producto p = new Modelo.Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagenPath(rs.getString("imagen_path"));
                p.setDisponible(rs.getInt("disponible") == 1);
                lista.add(p);
            }
        }
    } catch (Exception e) {
        System.err.println("Error al buscar productos: " + e.getMessage());
    }
    return lista;
}
    
    
public java.util.List<Object[]> obtenerUsuariosParaTabla() {
    java.util.List<Object[]> lista = new java.util.ArrayList<>();
    String sql = "SELECT u.id_usuario, u.nombre, u.usuario, u.clave, u.correo, r.nombre AS nombre_rol " +
                 "FROM usuarios u " +
                 "INNER JOIN roles r ON u.id_rol = r.id_rol";

    try (Connection con = ConexionMySQL.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] fila = new Object[]{
                false,                        // Columna 0: Checkbox
                rs.getInt("id_usuario"),     // Columna 1: ID
                rs.getString("nombre"),     // Columna 2: Nombre
                rs.getString("usuario"),    // Columna 3: Usuario
                rs.getString("clave"),      // Columna 4: Contraseña
                rs.getString("nombre_rol"), // Columna 5: Rol
                rs.getString("correo"),     // Columna 6: Correo
                ""                            // Columna 7: Acciones
            };
            lista.add(fila);
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios: " + e.getMessage());
    }
    return lista;
}
}