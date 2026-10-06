package Conexion;

import Modelo.ItemPedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/** Operaciones de cobro que deben ejecutarse como una sola transacción. */
public class PagoDAO {

    public static class ResultadoPago {
        public final boolean ok;
        public final int idPedido;
        public final String mensaje;

        public ResultadoPago(boolean ok, int idPedido, String mensaje) {
            this.ok = ok;
            this.idPedido = idPedido;
            this.mensaje = mensaje;
        }
    }

    public static class CuponInfo {
        public final boolean valido;
        public final double porcentaje;
        public final String mensaje;

        public CuponInfo(boolean valido, double porcentaje, String mensaje) {
            this.valido = valido;
            this.porcentaje = porcentaje;
            this.mensaje = mensaje;
        }
    }

    public CuponInfo validarCupon(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return new CuponInfo(false, 0, "Ingrese un código de cupón.");
        }
        String sql = "SELECT porcentaje_descuento FROM cupones "
                   + "WHERE UPPER(codigo)=UPPER(?) AND activo=1 "
                   + "AND (fecha_inicio IS NULL OR fecha_inicio <= CURDATE()) "
                   + "AND (fecha_fin IS NULL OR fecha_fin >= CURDATE())";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (con == null) return new CuponInfo(false, 0, "No hay conexión con MySQL.");
            ps.setString(1, codigo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CuponInfo(true, rs.getDouble(1), "Cupón aplicado.");
                }
            }
            return new CuponInfo(false, 0, "Cupón inválido o vencido.");
        } catch (Exception e) {
            return new CuponInfo(false, 0, "No se pudo validar el cupón.\n" + e.getMessage());
        }
    }

    public double consultarSaldoTarjetaRegalo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return -1;
        String sql = "SELECT saldo FROM tarjetas_regalo WHERE UPPER(codigo)=UPPER(?) AND activa=1";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (con == null) return -1;
            ps.setString(1, codigo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public ResultadoPago registrarPago(
            int idUsuario,
            List<ItemPedido> items,
            double subtotalSinImpuesto,
            double descuento,
            double total,
            String metodo,
            Double efectivoRecibido,
            Double cambio,
            String codigoTarjetaRegalo,
            boolean factura,
            String nombreFactura,
            String nitFactura,
            String correoFactura,
            String direccionFactura) {

        Connection con = null;
        try {
            con = ConexionMySQL.conectar();
            if (con == null) return new ResultadoPago(false, -1, "No se pudo conectar con MySQL.");
            con.setAutoCommit(false);

            int idPedido;
            String sqlPedido = "INSERT INTO pedidos (id_usuario, subtotal, descuento, total, estado) VALUES (?, ?, ?, ?, 'PAGADO')";
            try (PreparedStatement ps = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idUsuario);
                ps.setDouble(2, subtotalSinImpuesto);
                ps.setDouble(3, descuento);
                ps.setDouble(4, total);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) throw new Exception("No se pudo obtener el número del pedido.");
                    idPedido = rs.getInt(1);
                }
            }

            String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                for (ItemPedido item : items) {
                    if (item.getIdProducto() <= 0) {
                        throw new Exception("El producto '" + item.getNombre() + "' no tiene id_producto válido.");
                    }
                    ps.setInt(1, idPedido);
                    ps.setInt(2, item.getIdProducto());
                    ps.setInt(3, item.getCantidad());
                    ps.setDouble(4, item.getPrecioUnitario());
                    ps.setDouble(5, item.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            if ("REGALO".equalsIgnoreCase(metodo)) {
                double saldo = -1;
                String q = "SELECT saldo FROM tarjetas_regalo WHERE UPPER(codigo)=UPPER(?) AND activa=1 FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(q)) {
                    ps.setString(1, codigoTarjetaRegalo == null ? "" : codigoTarjetaRegalo.trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) saldo = rs.getDouble(1);
                    }
                }
                if (saldo < total) throw new Exception("La tarjeta de regalo no tiene saldo suficiente.");
                try (PreparedStatement ps = con.prepareStatement("UPDATE tarjetas_regalo SET saldo=saldo-? WHERE UPPER(codigo)=UPPER(?)")) {
                    ps.setDouble(1, total);
                    ps.setString(2, codigoTarjetaRegalo.trim());
                    ps.executeUpdate();
                }
            }

            String sqlPago = "INSERT INTO pagos (id_pedido, metodo, total_pagado, efectivo_recibido, cambio) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlPago)) {
                ps.setInt(1, idPedido);
                ps.setString(2, metodo);
                ps.setDouble(3, total);
                if (efectivoRecibido == null) ps.setNull(4, Types.DECIMAL); else ps.setDouble(4, efectivoRecibido);
                if (cambio == null) ps.setNull(5, Types.DECIMAL); else ps.setDouble(5, cambio);
                ps.executeUpdate();
            }

            if (factura) {
                String sqlFactura = "INSERT INTO facturas (id_pedido, nombre, nit, correo, direccion, total) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlFactura)) {
                    ps.setInt(1, idPedido);
                    ps.setString(2, nombreFactura);
                    ps.setString(3, (nitFactura == null || nitFactura.trim().isEmpty()) ? "CF" : nitFactura.trim());
                    ps.setString(4, correoFactura);
                    ps.setString(5, direccionFactura);
                    ps.setDouble(6, total);
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO historial (id_usuario, accion, descripcion) VALUES (?, 'PAGO', ?)")) {
                ps.setInt(1, idUsuario);
                ps.setString(2, "Pedido #" + idPedido + " pagado con " + metodo + " por Q " + String.format("%.2f", total));
                ps.executeUpdate();
            }

            con.commit();
            return new ResultadoPago(true, idPedido, "Pago registrado correctamente.");
        } catch (Exception e) {
            if (con != null) {
                try { con.rollback(); } catch (Exception ignored) {}
            }
            return new ResultadoPago(false, -1, e.getMessage());
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Consulta los registros de la tabla 'pagos' alineados con la tabla de la interfaz.
     * Estructura devuelta por fila:
     * [0] Checkbox (Boolean)
     * [1] ID Pago (Integer)
     * [2] ID Pedido (Integer)
     * [3] Método (String)
     * [4] Total Pagado (String)
     * [5] Efectivo Recibido (String)
     * [6] Cambio (String)
     * [7] Fecha y Hora (String)
     * [8] Acciones (String vació)
     */
    public List<Object[]> obtenerPagosParaTabla() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_pago, id_pedido, metodo, total_pagado, efectivo_recibido, cambio, fecha_hora "
                   + "FROM pagos ORDER BY id_pago DESC";

        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (con == null) return lista;

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = false;                                                    // Checkbox
                fila[1] = rs.getInt("id_pago");                                     // ID Pago
                fila[2] = rs.getInt("id_pedido");                                   // ID Pedido
                fila[3] = rs.getString("metodo");                                   // Método
                fila[4] = String.format("Q %.2f", rs.getDouble("total_pagado"));    // Total Pagado
                
                double efectivo = rs.getDouble("efectivo_recibido");
                fila[5] = rs.wasNull() ? "N/A" : String.format("Q %.2f", efectivo); // Efectivo Recibido
                
                double cambioVal = rs.getDouble("cambio");
                fila[6] = rs.wasNull() ? "N/A" : String.format("Q %.2f", cambioVal);// Cambio
                
                fila[7] = rs.getTimestamp("fecha_hora").toString();                // Fecha y Hora
                fila[8] = "";                                                       // Acciones

                lista.add(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}