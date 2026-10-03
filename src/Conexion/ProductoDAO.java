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

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagenPath(rs.getString("imagen_path"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                int sub=rs.getInt("id_subcategoria");p.setIdSubcategoria(rs.wasNull()?null:sub);
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

        try (java.sql.Connection con = Conexion.ConexionMySQL.conectar(); java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

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
        String sql = "SELECT u.id_usuario, u.nombre, u.usuario, u.clave, u.correo, r.nombre AS nombre_rol "
                + "FROM usuarios u "
                + "INNER JOIN roles r ON u.id_rol = r.id_rol";

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[]{
                    false, // Columna 0: Checkbox
                    rs.getInt("id_usuario"), // Columna 1: ID
                    rs.getString("nombre"), // Columna 2: Nombre
                    rs.getString("usuario"), // Columna 3: Usuario
                    rs.getString("clave"), // Columna 4: Contraseña
                    rs.getString("nombre_rol"), // Columna 5: Rol
                    rs.getString("correo"), // Columna 6: Correo
                    "" // Columna 7: Acciones
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
        String sql = "SELECT p.id_pedido, u.usuario AS nombre_usuario, p.fecha_hora, "
                + "p.subtotal, p.descuento, p.total, p.estado "
                + "FROM pedidos p "
                + "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario "
                + "ORDER BY p.id_pedido DESC";

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[]{
                    false, // Columna 0: Checkbox
                    rs.getInt("id_pedido"), // Columna 1: ID
                    rs.getString("nombre_usuario"), // Columna 2: Usuario
                    rs.getTimestamp("fecha_hora").toString(), // Columna 3: Fecha/Hora
                    "Q " + String.format("%.2f", rs.getDouble("subtotal")), // Columna 4: Subtotal
                    "Q " + String.format("%.2f", rs.getDouble("descuento")), // Columna 5: Descuento
                    "Q " + String.format("%.2f", rs.getDouble("total")), // Columna 6: Total
                    rs.getString("estado"), // Columna 7: Estado
                    "" // Columna 8: Acciones
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

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

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

        try (Connection con = ConexionMySQL.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }

// Mostrar todos los productos
public List<Object[]> obtenerProductosTabla() {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT p.id_producto, p.nombre, p.descripcion, "
            + "p.precio, p.imagen_path, p.disponible, "
            + "c.nombre AS nombreCategoria "
            + "FROM productos p "
            + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
            + "ORDER BY p.id_producto DESC";

    try (Connection con = ConexionMySQL.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            lista.add(new Object[]{
                false,
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                "Q " + String.format("%.2f", rs.getDouble("precio")),
                rs.getString("imagen_path"),
                rs.getString("nombreCategoria"),
                rs.getBoolean("disponible"),
                ""
            });
        }

    } catch (SQLException e) {
        System.err.println(
                "Error al obtener productos: " + e.getMessage());
    }

    return lista;
}

// Mostrar productos de una categoría
public List<Object[]> obtenerProductosTablaPorCategoria(int idCategoria)
        throws SQLException {

    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT p.id_producto, p.nombre, p.descripcion, "
            + "p.precio, p.imagen_path, p.disponible, "
            + "c.nombre AS nombreCategoria "
            + "FROM productos p "
            + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
            + "WHERE p.id_categoria = ? "
            + "ORDER BY p.id_producto DESC";

    try (Connection con = ConexionMySQL.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idCategoria);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    false,
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    "Q " + String.format("%.2f", rs.getDouble("precio")),
                    rs.getString("imagen_path"),
                    rs.getString("nombreCategoria"),
                    rs.getBoolean("disponible"),
                    ""
                });
            }
        }
    }

    return lista;
}


    // CRUD de productos: las excepciones se muestran en la pantalla, sin ocultarlas.
    public Modelo.Producto obtenerProductoPorId(int id) throws SQLException {
        try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("SELECT * FROM productos WHERE id_producto=?")) {
            s.setInt(1,id);try(ResultSet r=s.executeQuery()) {
                if(!r.next()) return null;
                Modelo.Producto p=new Modelo.Producto();p.setIdProducto(r.getInt("id_producto"));p.setNombre(r.getString("nombre"));p.setDescripcion(r.getString("descripcion"));p.setPrecio(r.getDouble("precio"));p.setImagenPath(r.getString("imagen_path"));p.setIdCategoria(r.getInt("id_categoria"));p.setDisponible(r.getBoolean("disponible"));
                int sub=r.getInt("id_subcategoria");p.setIdSubcategoria(r.wasNull()?null:sub);return p;
            }
        }
    }
    public List<Object[]> categoriasCrud() throws SQLException {
        List<Object[]> r=new ArrayList<>();try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("SELECT id_categoria,nombre FROM categorias ORDER BY nombre");ResultSet x=s.executeQuery()){while(x.next())r.add(new Object[]{x.getInt(1),x.getString(2)});}return r;
    }
    public List<Object[]> subcategoriasCrud() throws SQLException {
        List<Object[]> r=new ArrayList<>();try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("SELECT id_subcategoria,nombre,id_categoria FROM subcategorias ORDER BY nombre");ResultSet x=s.executeQuery()){while(x.next())r.add(new Object[]{x.getInt(1),x.getString(2),x.getInt(3)});}return r;
    }
    private void validarProducto(Connection c,Modelo.Producto p,java.math.BigDecimal precio) throws SQLException {
        if(p.getNombre()==null||p.getNombre().trim().isEmpty()||p.getNombre().length()>100)throw new SQLException("Nombre requerido, máximo 100 caracteres.");
        if(p.getDescripcion()!=null&&p.getDescripcion().length()>255)throw new SQLException("Descripción demasiado larga.");
        if(p.getImagenPath()!=null&&p.getImagenPath().length()>255)throw new SQLException("Ruta de imagen demasiado larga.");
        if(precio==null||precio.signum()<0||precio.scale()>2||precio.compareTo(new java.math.BigDecimal("99999999.99"))>0)throw new SQLException("Precio inválido, máximo 2 decimales.");
        try(PreparedStatement s=c.prepareStatement("SELECT id_categoria FROM categorias WHERE id_categoria=?")){s.setInt(1,p.getIdCategoria());try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("Categoría inexistente.");}}
        if(p.getIdSubcategoria()!=null)try(PreparedStatement s=c.prepareStatement("SELECT id_subcategoria FROM subcategorias WHERE id_subcategoria=? AND id_categoria=?")){s.setInt(1,p.getIdSubcategoria());s.setInt(2,p.getIdCategoria());try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("La subcategoría no pertenece a esa categoría.");}}
    }
    public int guardarProducto(Modelo.Producto p,java.math.BigDecimal precio,boolean nuevo,String grupoExtra) throws SQLException {
        try(Connection c=ConexionMySQL.conectar()) {
            c.setAutoCommit(false);
            try {
                validarProducto(c,p,precio);
                String q=nuevo?"INSERT INTO productos(nombre,descripcion,precio,imagen_path,id_categoria,id_subcategoria,disponible) VALUES(?,?,?,?,?,?,?)":"UPDATE productos SET nombre=?,descripcion=?,precio=?,imagen_path=?,id_categoria=?,id_subcategoria=?,disponible=? WHERE id_producto=?";
                int id=p.getIdProducto();
                try(PreparedStatement s=c.prepareStatement(q,Statement.RETURN_GENERATED_KEYS)) {
                    s.setString(1,p.getNombre().trim());s.setString(2,p.getDescripcion());s.setBigDecimal(3,precio);s.setString(4,p.getImagenPath());s.setInt(5,p.getIdCategoria());if(p.getIdSubcategoria()==null)s.setNull(6,java.sql.Types.INTEGER);else s.setInt(6,p.getIdSubcategoria());s.setBoolean(7,p.isDisponible());if(!nuevo)s.setInt(8,id);
                    int filas=s.executeUpdate();if(nuevo){try(ResultSet r=s.getGeneratedKeys()){if(!r.next())throw new SQLException("No se obtuvo el ID del producto.");id=r.getInt(1);}}
                    else if(filas==0){try(PreparedStatement existe=c.prepareStatement("SELECT id_producto FROM productos WHERE id_producto=?")){existe.setInt(1,id);try(ResultSet r=existe.executeQuery()){if(!r.next())throw new SQLException("El producto ya no existe.");}}}
                }
                if(nuevo&&("PAPAS".equals(grupoExtra)||"COMBOS".equals(grupoExtra)))try(PreparedStatement s=c.prepareStatement("INSERT INTO tarjetas_productos_admin(grupo,id_producto) VALUES(?,?)")){s.setString(1,grupoExtra);s.setInt(2,id);s.executeUpdate();}
                c.commit();return id;
            }catch(SQLException e){c.rollback();throw e;}
        }
    }
    public boolean eliminarProducto(int id) throws SQLException {
        try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("DELETE FROM productos WHERE id_producto=?")){s.setInt(1,id);return s.executeUpdate()>0;}
    }
    public boolean cambiarDisponibilidadProducto(int id,boolean disponible) throws SQLException {
        try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("UPDATE productos SET disponible=? WHERE id_producto=?")){s.setBoolean(1,disponible);s.setInt(2,id);return s.executeUpdate()>0;}
    }

} // Fin de ProductoDAO