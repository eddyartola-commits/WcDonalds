package Componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Línea visual del carrito con controles de +, - visibles y alineación uniforme.
 */
public class ItemOrden extends JPanel {

    private final int idProducto;
    private final String nombreProducto;
    private final double precioUnitario;
    private final String imagenPath;
    private int cantidad = 1;

    private JLabel lblCantidad;
    private JLabel lblPrecioTotal;
    private Runnable onCantidadChange;

    public ItemOrden(String nombre, double precio, Image imagenProducto, Runnable onCantidadChange) {
        this(0, nombre, precio, imagenProducto, null, onCantidadChange);
    }

    public ItemOrden(int idProducto, String nombre, double precio, Image imagenProducto,
                     String imagenPath, Runnable onCantidadChange) {
        this.idProducto = idProducto;
        this.nombreProducto = nombre;
        this.precioUnitario = precio;
        this.imagenPath = imagenPath;
        this.onCantidadChange = onCantidadChange;

        setOpaque(false);
        setLayout(new BorderLayout(12, 0));
        
        setPreferredSize(new Dimension(340, 90));
        setMinimumSize(new Dimension(280, 90));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        // Margen equilibrado para centrar el contenido (izq: 8, der: 15)
        setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 15));

        // 1. IMAGEN DEL PRODUCTO (GRANDE Y CENTRADA VERTICALMENTE)
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        
        if (imagenProducto != null) {
            Image imgEscalada = imagenProducto.getScaledInstance(76, 76, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        }
        add(lblImagen, BorderLayout.WEST);

        // 2. PANEL CENTRAL (Nombre y Controles + / -)
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel("<html><body style='width: 125px;'>" + nombreProducto + "</body></html>");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(new Color(30, 30, 30));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Controles de Incremento (+ / -) con botones redondos dibujados manualmente
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelControles.setOpaque(false);
        panelControles.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnMenos = crearBotonSimbologia("-", new Color(240, 240, 240), new Color(30, 30, 30));
        
        lblCantidad = new JLabel("1", SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCantidad.setPreferredSize(new Dimension(20, 26));
        lblCantidad.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        
        JButton btnMas = crearBotonSimbologia("+", new Color(240, 240, 240), new Color(30, 30, 30));

        btnMenos.addActionListener(e -> {
            if (cantidad > 1) {
                cantidad--;
                actualizarItem();
            }
        });
        
        btnMas.addActionListener(e -> {
           // Si la regla de negocio limita a 1 unidad por disponibilidad
    if (cantidad >= 1) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Alcanzaste el límite de unidades disponibles para este producto.",
            "Límite Alcanzado",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
    } else {
        cantidad++;
        actualizarItem();
    }
        });

        panelControles.add(btnMenos);
        panelControles.add(lblCantidad);
        panelControles.add(btnMas);

        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(lblNombre);
        panelCentro.add(Box.createVerticalStrut(4));
        panelCentro.add(panelControles);
        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);

        // 3. PANEL DERECHO (Basurero arriba, Precio abajo)
        JPanel panelDerecha = new JPanel();
        panelDerecha.setOpaque(false);
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

        JButton btnEliminar = new JButton("🗑");
        btnEliminar.setToolTipText("Eliminar producto");
        btnEliminar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btnEliminar.setForeground(new Color(130, 130, 130));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        btnEliminar.addActionListener(e -> {
            Container parent = getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
                if (onCantidadChange != null) onCantidadChange.run();
            }
        });

        lblPrecioTotal = new JLabel(String.format("Q %.2f", precioUnitario));
        lblPrecioTotal.setFont(new Font("Arial Black", Font.BOLD, 15));
        lblPrecioTotal.setForeground(new Color(218, 41, 28)); // Rojo McDonald's
        lblPrecioTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panelDerecha.add(Box.createVerticalGlue());
        panelDerecha.add(btnEliminar);
        panelDerecha.add(Box.createVerticalStrut(10));
        panelDerecha.add(lblPrecioTotal);
        panelDerecha.add(Box.createVerticalGlue());
        
        add(panelDerecha, BorderLayout.EAST);
    }

    private void actualizarItem() {
        lblCantidad.setText(String.valueOf(cantidad));
        lblPrecioTotal.setText(String.format("Q %.2f", cantidad * precioUnitario));
        if (onCantidadChange != null) onCantidadChange.run();
    }

    /**
     * Dibuja los botones redondos con el símbolo +, - vectorizado para evitar que Swing renderice "..."
     */
    private JButton crearBotonSimbologia(String simbolo, Color bg, Color fg) {
        JButton btn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = getWidth();
                int h = getHeight();

                // 1. Dibujar Círculo de fondo
                g2.setColor(bg);
                g2.fillOval(0, 0, w - 1, h - 1);

                // 2. Borde sutil
                g2.setColor(new Color(220, 220, 220));
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawOval(0, 0, w - 1, h - 1);

                // 3. Dibujar manualmente los símbolos "+" y "-" para evitar recortes de fuente
                g2.setColor(fg);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                
                int centroX = w / 2;
                int centroY = h / 2;
                int radio = 4;

                // Línea Horizontal (presente tanto en - como en +)
                g2.drawLine(centroX - radio, centroY, centroX + radio, centroY);

                // Línea Vertical (solo para +)
                if ("+".equals(simbolo)) {
                    g2.drawLine(centroX, centroY - radio, centroX, centroY + radio);
                }

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(26, 26));
        btn.setMaximumSize(new Dimension(26, 26));
        btn.setMinimumSize(new Dimension(26, 26));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public int getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }
    public String getImagenPath() { return imagenPath; }
    public double getSubtotal() { return cantidad * precioUnitario; }
}