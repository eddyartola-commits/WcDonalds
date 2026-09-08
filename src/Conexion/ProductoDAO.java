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
}