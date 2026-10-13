package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Cargar todas las ventas para la JTable
    public List<Object[]> listarVentas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT p.id_pedido, u.usuario, p.fecha_hora, p.subtotal, p.descuento, p.total, p.estado " +
                     "FROM pedidos p INNER JOIN usuarios u ON p.id_usuario = u.id_usuario ORDER BY p.id_pedido DESC";

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_pedido"),
                    rs.getString("usuario"),
                    rs.getTimestamp("fecha_hora"),
                    rs.getBigDecimal("subtotal"),
                    rs.getBigDecimal("descuento"),
                    rs.getBigDecimal("total"),
                    rs.getString("estado")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    // Buscar una venta por su ID Pedido
    public Object[] buscarPorId(int idPedido) {
        String sql = "SELECT id_pedido, id_usuario, fecha_hora, subtotal, descuento, total, estado " +
                     "FROM pedidos WHERE id_pedido = ?";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                        rs.getInt("id_pedido"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_hora"),
                        rs.getBigDecimal("subtotal"),
                        rs.getBigDecimal("descuento"),
                        rs.getBigDecimal("total"),
                        rs.getString("estado")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pedido: " + e.getMessage());
        }
        return null;
    }

    // Insertar un nuevo pedido (CREAR USUARIO / GUARDAR)
    public boolean insertar(int idUsuario, double subtotal, double descuento, double total, String estado) {
        String sql = "INSERT INTO pedidos (id_usuario, subtotal, descuento, total, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setDouble(2, subtotal);
            ps.setDouble(3, descuento);
            ps.setDouble(4, total);
            ps.setString(5, estado);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar pedido: " + e.getMessage());
            return false;
        }
    }

    // Actualizar pedido (ACTUALIZAR)
    public boolean actualizar(int idPedido, int idUsuario, double subtotal, double descuento, double total, String estado) {
        String sql = "UPDATE pedidos SET id_usuario = ?, subtotal = ?, descuento = ?, total = ?, estado = ? WHERE id_pedido = ?";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setDouble(2, subtotal);
            ps.setDouble(3, descuento);
            ps.setDouble(4, total);
            ps.setString(5, estado);
            ps.setInt(6, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    // Eliminar pedido (BORRAR)
    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }
}