package Modelo;

import Componentes.PanelRedondeadoSombra;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MoldeProductos extends PanelRedondeadoSombra {

    private JLabel lblImagen;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnAgregar;
    private Modelo.Producto producto;

    // Colores de la tarjeta
    private final Color COLOR_NORMAL = Color.WHITE;
    private final Color COLOR_HOVER = new Color(255, 188, 13);  // Amarillo McDonald's
    private final Color COLOR_CLICK = new Color(230, 160, 0);

    // Color del botón rojo (+)
    private final Color BTN_ROJO_NORMAL = new Color(218, 41, 28);   // Rojo principal
    private final Color BTN_ROJO_CLICK = new Color(175, 25, 15);     // Rojo oscuro al presionar

    public MoldeProductos() {
        initComponents();
        configurarHoverYClick();
    }

    private void initComponents() {
        setLayout(new BorderLayout(5, 5));

        Dimension tamanoFijo = new Dimension(180, 220);
        setPreferredSize(tamanoFijo);
        setMinimumSize(tamanoFijo);
        setMaximumSize(tamanoFijo);
        setSize(tamanoFijo);

        setBackground(COLOR_NORMAL);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(12, 14, 14, 14));

        // 1. Imagen centrada arriba
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        // 2. Nombre del producto
        lblNombre = new JLabel("Producto");
        lblNombre.setFont(new Font("Arial Black", Font.BOLD, 12));
        lblNombre.setForeground(new Color(20, 20, 20));
        lblNombre.setBorder(new EmptyBorder(12, 0, 8, 0));

        // 3. Fila del Precio y Botón (+)
        JPanel panelFilaPrecio = new JPanel(new BorderLayout());
        panelFilaPrecio.setOpaque(false);

        lblPrecio = new JLabel("Q 0.00");
        lblPrecio.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblPrecio.setForeground(BTN_ROJO_NORMAL);

        // Botón con animación física de clic (Presionar/Hundir)
        btnAgregar = new JButton("+") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Si está presionado, encoge un 10% para simular hundimiento
                if (getModel().isPressed()) {
                    g2.scale(0.9, 0.9);
                    g2.translate(w * 0.05, h * 0.05);
                    g2.setColor(BTN_ROJO_CLICK);
                } else {
                    g2.setColor(BTN_ROJO_NORMAL);
                }

                g2.fillRoundRect(0, 0, w, h, 16, 16);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btnAgregar.setFont(new Font("Arial Black", Font.BOLD, 18));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setMargin(new Insets(0, 0, 0, 0));
        btnAgregar.setPreferredSize(new Dimension(38, 38));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFilaPrecio.add(lblPrecio, BorderLayout.WEST);
        panelFilaPrecio.add(btnAgregar, BorderLayout.EAST);

        // Contenedor inferior agrupado
        JPanel panelInferiorAgrupado = new JPanel(new BorderLayout());
        panelInferiorAgrupado.setOpaque(false);
        panelInferiorAgrupado.add(lblNombre, BorderLayout.NORTH);
        panelInferiorAgrupado.add(panelFilaPrecio, BorderLayout.SOUTH);

        add(lblImagen, BorderLayout.CENTER);
        add(panelInferiorAgrupado, BorderLayout.SOUTH);
    }

    private void configurarHoverYClick() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(COLOR_HOVER);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(COLOR_NORMAL);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(COLOR_CLICK);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getBounds().contains(e.getPoint())) {
                    setBackground(COLOR_HOVER);
                } else {
                    setBackground(COLOR_NORMAL);
                }
                repaint();
            }
        };

        this.addMouseListener(adapter);
        lblNombre.addMouseListener(adapter);
        lblImagen.addMouseListener(adapter);
        lblPrecio.addMouseListener(adapter);
    }

    public void setDatos(Modelo.Producto p) {
        this.producto = p;
        lblNombre.setText("<html><div style='width: 110px;'>" + p.getNombre() + "</div></html>");
        lblPrecio.setText("Q " + String.format("%.2f", p.getPrecio()));

        String ruta = p.getImagenPath();

        if (ruta != null && !ruta.trim().isEmpty()) {
            File archivo = new File(ruta.trim());

            if (archivo.exists()) {
                ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
            }
        }
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public Modelo.Producto getProducto() {
        return producto;
    }
}