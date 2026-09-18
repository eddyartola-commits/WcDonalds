package Conexion;

import Modelo.Producto;
import java.sql.Statement;
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

// Método formateado para enviar filas directamente al componente Tabla
    public List<Object[]> obtenerPedidosParaTabla() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT p.id_pedido, u.usuario AS nombre_usuario, p.fecha_hora, " +
                     "p.subtotal, p.descuento, p.total, p.estado " +
                     "FROM pedidos p " +
                     "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "ORDER BY p.id_pedido DESC";

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[]{
                    false,                                                // Columna 0: Checkbox
                    rs.getInt("id_pedido"),                              // Columna 1: ID
                    rs.getString("nombre_usuario"),                       // Columna 2: Usuario
                    rs.getTimestamp("fecha_hora").toString(),            // Columna 3: Fecha/Hora
                    "Q " + String.format("%.2f", rs.getDouble("subtotal")),  // Columna 4: Subtotal
                    "Q " + String.format("%.2f", rs.getDouble("descuento")), // Columna 5: Descuento
                    "Q " + String.format("%.2f", rs.getDouble("total")),     // Columna 6: Total
                    rs.getString("estado"),                              // Columna 7: Estado
                    ""                                                   // Columna 8: Acciones
                };
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos para la tabla: " + e.getMessage());
        }
        return lista;
    }

    // Insertar un nuevo pedido en la base de datos
    public int registrarPedido(int idUsuario, double subtotal, double descuento, double total, String estado) {
        String sql = "INSERT INTO pedidos (id_usuario, subtotal, descuento, total, estado) VALUES (?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            ps.setDouble(2, subtotal);
            ps.setDouble(3, descuento);
            ps.setDouble(4, total);
            ps.setString(5, estado);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el pedido: " + e.getMessage());
        }
        return idGenerado;
    }

    // Cambiar el estado de un pedido
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado del pedido: " + e.getMessage());
            return false;
        }
    }
    
    // Elimina un pedido físicamente de la base de datos por su ID
public boolean eliminarPedido(int idPedido) {
    String sql = "DELETE FROM pedidos WHERE id_pedido = ?";

    try (Connection con = ConexionMySQL.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPedido);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al eliminar el pedido: " + e.getMessage());
        return false;
    }
}

}