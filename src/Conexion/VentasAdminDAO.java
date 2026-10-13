package Conexion;

import java.sql.*;
import java.math.BigDecimal;
import java.util.*;

/** Administración de pedidos que se muestran en la pantalla Ventas. */
public class VentasAdminDAO {
    private Connection conectar() throws SQLException {
        Connection con=ConexionMySQL.conectar();
        if(con==null)throw new SQLException("No se pudo conectar con MySQL.");
        return con;
    }
    public List<Object[]> listar() throws SQLException {
        List<Object[]> filas=new ArrayList<>();
        try(Connection con=conectar();PreparedStatement ps=con.prepareStatement(
            "SELECT id_pedido,id_usuario,fecha_hora,subtotal,descuento,total,estado FROM pedidos ORDER BY id_pedido DESC");
            ResultSet rs=ps.executeQuery()) {
            while(rs.next())filas.add(new Object[]{false,rs.getInt(1),rs.getInt(2),rs.getTimestamp(3),
                rs.getBigDecimal(4),rs.getBigDecimal(5),rs.getBigDecimal(6),rs.getString(7),""});
        }
        return filas;
    }
    public static BigDecimal calcularTotal(BigDecimal subtotal,BigDecimal descuento) {
        if(subtotal==null || descuento==null || subtotal.signum()<0 || descuento.signum()<0 || descuento.compareTo(subtotal)>0)
            throw new IllegalArgumentException("El descuento debe estar entre cero y el subtotal.");
        return subtotal.subtract(descuento);
    }
    public void guardar(Integer id,int usuario,BigDecimal subtotal,BigDecimal descuento,String estado) throws SQLException {
        BigDecimal total=calcularTotal(subtotal,descuento);
        if(usuario<=0 || estado==null || estado.trim().isEmpty())throw new SQLException("Usuario y estado son obligatorios.");
        try(Connection con=conectar()) {
            con.setAutoCommit(false);
            try {
                try(PreparedStatement ps=con.prepareStatement("SELECT id_usuario FROM usuarios WHERE id_usuario=?")) {
                    ps.setInt(1,usuario);try(ResultSet rs=ps.executeQuery()) {
                        if(!rs.next())throw new SQLException("El ID de usuario no existe.");
                    }
                }
                validarEstado(con,estado);
                if(id!=null) {
                    try(PreparedStatement ps=con.prepareStatement("SELECT subtotal,descuento,total FROM pedidos WHERE id_pedido=? FOR UPDATE")) {
                        ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()) {
                            if(!rs.next())throw new SQLException("El pedido ya no existe.");
                            boolean cambia=rs.getBigDecimal(1).compareTo(subtotal)!=0 || rs.getBigDecimal(2).compareTo(descuento)!=0 || rs.getBigDecimal(3).compareTo(total)!=0;
                            if(cambia && tieneReferencias(con,id))throw new SQLException("Este pedido tiene productos, pagos o factura. Sus importes deben corregirse desde su detalle o cobro.");
                        }
                    }
                }
                String sql=id==null ? "INSERT INTO pedidos(id_usuario,fecha_hora,subtotal,descuento,total,estado) VALUES(?,NOW(),?,?,?,?)"
                    : "UPDATE pedidos SET id_usuario=?,subtotal=?,descuento=?,total=?,estado=? WHERE id_pedido=?";
                try(PreparedStatement ps=con.prepareStatement(sql)) {
                    ps.setInt(1,usuario);ps.setBigDecimal(2,subtotal);ps.setBigDecimal(3,descuento);
                    ps.setBigDecimal(4,total);ps.setString(5,estado.trim().toUpperCase(Locale.ROOT));
                    if(id!=null)ps.setInt(6,id);
                    if(ps.executeUpdate()==0)throw new SQLException("No se encontró el pedido.");
                }
                con.commit();
            } catch(SQLException|RuntimeException e){con.rollback();throw e;}
            finally{con.setAutoCommit(true);}
        }
    }
    private void validarEstado(Connection con,String estado) throws SQLException {
        try(PreparedStatement ps=con.prepareStatement("SHOW COLUMNS FROM pedidos LIKE 'estado'");ResultSet rs=ps.executeQuery()) {
            if(rs.next()) {
                String tipo=rs.getString("Type");
                if(tipo!=null && tipo.toLowerCase(Locale.ROOT).startsWith("enum(")) {
                    java.util.regex.Matcher m=java.util.regex.Pattern.compile("'([^']*)'").matcher(tipo);
                    List<String> opciones=new ArrayList<>();
                    while(m.find())opciones.add(m.group(1));
                    for(String opcion:opciones)if(opcion.equalsIgnoreCase(estado.trim()))return;
                    throw new SQLException("Estado permitido: "+String.join(", ",opciones));
                }
            }
        }
    }
    private boolean tieneReferencias(Connection con,int id) throws SQLException {
        for(String tabla:new String[]{"detalle_pedido","pagos","facturas"}) {
            try(PreparedStatement ps=con.prepareStatement("SELECT 1 FROM "+tabla+" WHERE id_pedido=? LIMIT 1")) {
                ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){if(rs.next())return true;}
            }
        }
        return false;
    }
    public void eliminar(int id) throws SQLException {
        try(Connection con=conectar()) {
            con.setAutoCommit(false);
            try {
                try(PreparedStatement ps=con.prepareStatement("SELECT id_pedido FROM pedidos WHERE id_pedido=? FOR UPDATE")) {
                    ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){
                        if(!rs.next())throw new SQLException("El pedido ya no existe.");
                    }
                }
                if(tieneReferencias(con,id))throw new SQLException("El pedido tiene productos, pagos o factura. Conserva el pedido y modifica su estado según corresponda.");
                try(PreparedStatement ps=con.prepareStatement("DELETE FROM pedidos WHERE id_pedido=?")) {
                    ps.setInt(1,id);if(ps.executeUpdate()!=1)throw new SQLException("El pedido ya no existe.");
                }
                con.commit();
            }catch(SQLException e){con.rollback();throw e;}
            finally{con.setAutoCommit(true);}
        }
    }
}
