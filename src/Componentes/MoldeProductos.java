package Componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MoldeProductos extends PanelRedondeadoSombra {

    private JLabel lblImagen;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private Modelo.Producto producto;

    public MoldeProductos() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));
        
        // Dimensiones exactas de la tarjeta (Ancho: 180px, Alto: 220px)
        Dimension tamanoFijo = new Dimension(180, 220);
        setPreferredSize(tamanoFijo);
        setMinimumSize(tamanoFijo);
        setMaximumSize(tamanoFijo);
        setSize(tamanoFijo);
        
        setBackground(Color.WHITE);

        // 1. Nombre arriba
        lblNombre = new JLabel("Producto", SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setForeground(new Color(30, 30, 30));

        // 2. Imagen al centro con tamaño limitado
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        // 3. Precio abajo
        lblPrecio = new JLabel("Q 0.00", SwingConstants.CENTER);
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblPrecio.setForeground(new Color(218, 41, 28));

        add(lblNombre, BorderLayout.NORTH);
        add(lblImagen, BorderLayout.CENTER);
        add(lblPrecio, BorderLayout.SOUTH);
    }

    public void setDatos(Modelo.Producto p) {
        this.producto = p;
        lblNombre.setText(p.getNombre());
        lblPrecio.setText("Q " + String.format("%.2f", p.getPrecio()));

        String ruta = p.getImagenPath();

        if (ruta != null && !ruta.trim().isEmpty()) {
            File archivo = new File(ruta.trim());

            if (archivo.exists()) {
                ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
                // Escalar la hamburguesa de forma proporcional
                Image img = icon.getImage().getScaledInstance(120, 95, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
            }
        }
    }

    public Modelo.Producto getProducto() {
        return producto;
    }
}