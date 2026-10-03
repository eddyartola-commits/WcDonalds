package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Consultas de las tarjetas, sin modificar las categorías de la base. */
public class FiltroProductosDAO {
    public enum Grupo {
        HAMBURGUESAS("Hamburguesas"), POLLO("Pollo"), BEBIDAS("Bebidas"),
        POSTRES("Postres"), DESAYUNOS("Desayunos"), PAPAS("Papas"),
        CAJITA_FELIZ("Cajita Feliz"), COMBOS("Combos");
        private final String nombre;
        Grupo(String nombre) { this.nombre = nombre; }
        public String getNombre() { return nombre; }
    }

    private String condicion(Grupo grupo) {
        return TarjetasDAO.condicion(new TarjetasDAO.Tarjeta(grupo.name(),grupo.getNombre(),null,null));
    }
    private void parametros(PreparedStatement ps, Grupo grupo) throws SQLException {
        // La condición solo contiene IDs fijos y claves internas del enum.
    }

    public List<Object[]> obtenerTabla(Grupo grupo) throws SQLException {
        new TarjetasDAO().preparar();
        List<Object[]> filas = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, "
                + "p.precio, p.imagen_path, p.disponible, "
                + "c.nombre AS nombreCategoria FROM productos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE " + condicion(grupo) + " ORDER BY p.id_producto DESC";
        try (Connection con = ConexionMySQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            parametros(ps, grupo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    filas.add(new Object[]{false, rs.getInt("id_producto"),
                        rs.getString("nombre"), rs.getString("descripcion"),
                        "Q " + String.format("%.2f", rs.getDouble("precio")),
                        rs.getString("imagen_path"), rs.getString("nombreCategoria"),
                        rs.getBoolean("disponible"), ""});
                }
            }
        }
        return filas;
    }

    public int[] obtenerCantidades(Grupo[] grupos) throws SQLException {
        new TarjetasDAO().preparar();
        int[] cantidades = new int[grupos.length];
        try (Connection con = ConexionMySQL.conectar()) {
            for (int i = 0; i < grupos.length; i++) {
                String sql = "SELECT COUNT(*) FROM productos p WHERE "
                        + condicion(grupos[i]);
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    parametros(ps, grupos[i]);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) cantidades[i] = rs.getInt(1);
                    }
                }
            }
        }
        return cantidades;
    }
}
