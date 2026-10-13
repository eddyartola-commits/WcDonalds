package Conexion;

import java.sql.*;
import java.util.*;

/** Metadatos de tarjetas nuevas y asignaciones de Papas/Combos. */
public class TarjetasDAO {
    public static class Tarjeta {
        public String clave, nombre, imagen;
        public Integer categoria;
        public int cantidad;
        public Tarjeta(String clave,String nombre,Integer categoria,String imagen) {
            this.clave=clave; this.nombre=nombre; this.categoria=categoria; this.imagen=imagen;
        }
        @Override public String toString(){return nombre;}
    }
    public void preparar() throws SQLException {
        try(Connection c=ConexionMySQL.conectar(); Statement s=c.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS tarjetas_admin (id_categoria INT PRIMARY KEY, imagen_path VARCHAR(1024), FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria))");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS tarjetas_productos_admin (grupo VARCHAR(40) NOT NULL, id_producto INT NOT NULL, PRIMARY KEY(grupo,id_producto), FOREIGN KEY(id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE)");
        }
    }
    public List<Tarjeta> nuevas() throws SQLException {
        List<Tarjeta> r=new ArrayList<>();
        String q="SELECT c.id_categoria,c.nombre,t.imagen_path FROM tarjetas_admin t JOIN categorias c ON c.id_categoria=t.id_categoria ORDER BY c.nombre";
        try(Connection c=ConexionMySQL.conectar(); PreparedStatement s=c.prepareStatement(q); ResultSet x=s.executeQuery()) {
            while(x.next())r.add(new Tarjeta("CAT_"+x.getInt(1),x.getString(2),x.getInt(1),x.getString(3)));
        } return r;
    }
    public int crear(String nombre,String imagen) throws SQLException {
        try(Connection c=ConexionMySQL.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try(PreparedStatement s=c.prepareStatement("INSERT INTO categorias(nombre,estado) VALUES(?,1)",Statement.RETURN_GENERATED_KEYS)) {
                    s.setString(1,nombre);s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){if(!r.next())throw new SQLException("No se obtuvo el ID");id=r.getInt(1);}
                }
                try(PreparedStatement s=c.prepareStatement("INSERT INTO tarjetas_admin(id_categoria,imagen_path) VALUES(?,?)")) {
                    s.setInt(1,id);s.setString(2,imagen);s.executeUpdate();
                }c.commit();return id;
            }catch(SQLException e){c.rollback();throw e;}
        }
    }
    public static String condicion(Tarjeta t) {
        if(t.categoria!=null)return "p.id_categoria="+t.categoria;
        switch(t.clave) {
            case "HAMBURGUESAS":return "p.id_subcategoria=3";
            case "POLLO":return "p.id_subcategoria=4";
            case "CAJITA_FELIZ":return "p.id_subcategoria=1";
            case "BEBIDAS":return "p.id_categoria=3";
            case "POSTRES":return "p.id_categoria=7";
            case "DESAYUNOS":return "p.id_categoria=4";
            case "PAPAS":return "(p.id_producto IN(55,60) OR EXISTS(SELECT 1 FROM tarjetas_productos_admin a WHERE a.id_producto=p.id_producto AND a.grupo='PAPAS'))";
            case "COMBOS":return "EXISTS(SELECT 1 FROM tarjetas_productos_admin a WHERE a.id_producto=p.id_producto AND a.grupo='COMBOS')";
            default:throw new IllegalArgumentException("Tarjeta desconocida");
        }
    }
    public List<Object[]> productos(Tarjeta t) throws SQLException {
        List<Object[]> r=new ArrayList<>();
        String q="SELECT p.id_producto,p.nombre,p.precio,p.disponible,c.nombre,p.imagen_path,p.descripcion FROM productos p JOIN categorias c ON c.id_categoria=p.id_categoria"+(t==null?"":" WHERE "+condicion(t))+" ORDER BY p.nombre";
        try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement(q);ResultSet x=s.executeQuery()) {
            while(x.next())r.add(new Object[]{x.getInt(1),x.getString(2),x.getBigDecimal(3),x.getBoolean(4),x.getString(5),x.getString(6),x.getString(7)});
        }return r;
    }
    public int contar(Tarjeta t) throws SQLException {
        try(Connection c=ConexionMySQL.conectar();PreparedStatement s=c.prepareStatement("SELECT COUNT(*) FROM productos p WHERE "+condicion(t));ResultSet r=s.executeQuery()){r.next();return r.getInt(1);}
    }
    public void asignar(Tarjeta t,List<Integer> ids) throws SQLException {
        boolean lista=t.clave.equals("PAPAS")||t.clave.equals("COMBOS");
        Integer cat=t.categoria,sub=null;
        if(cat==null&&!lista) {
            switch(t.clave) {
                case "HAMBURGUESAS":cat=1;sub=3;break;
                case "POLLO":cat=1;sub=4;break;
                case "CAJITA_FELIZ":cat=1;sub=1;break;
                case "BEBIDAS":cat=3;break;
                case "POSTRES":cat=7;break;
                case "DESAYUNOS":cat=4;break;
                default:throw new SQLException("Grupo no válido");
            }
        }
        try(Connection c=ConexionMySQL.conectar()) {
            c.setAutoCommit(false);
            try {
                String q=lista?"INSERT IGNORE INTO tarjetas_productos_admin(grupo,id_producto) VALUES(?,?)":"UPDATE productos SET id_categoria=?,id_subcategoria=? WHERE id_producto=?";
                try(PreparedStatement s=c.prepareStatement(q)) {
                    for(int id:ids){if(lista){s.setString(1,t.clave);s.setInt(2,id);}else{s.setInt(1,cat);if(sub==null)s.setNull(2,Types.INTEGER);else s.setInt(2,sub);s.setInt(3,id);}s.addBatch();}s.executeBatch();
                }c.commit();
            }catch(SQLException e){c.rollback();throw e;}
        }
    }
    public int disponibilidad(Tarjeta t,boolean disponible) throws SQLException {
        // Materializa IDs antes de actualizar para evitar la restricción de subconsultas de MySQL.
        List<Object[]> filas=productos(t);
        try(Connection c=ConexionMySQL.conectar()) {
            c.setAutoCommit(false);
            try(PreparedStatement s=c.prepareStatement("UPDATE productos SET disponible=? WHERE id_producto=?")) {
                for(Object[] f:filas){s.setBoolean(1,disponible);s.setInt(2,(Integer)f[0]);s.addBatch();}s.executeBatch();c.commit();return filas.size();
            }catch(SQLException e){c.rollback();throw e;}
        }
    }
}
