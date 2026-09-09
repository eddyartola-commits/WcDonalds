package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ItemOrden extends JPanel {

    private String nombreProducto;
    private double precioUnitario;
    private int cantidad = 1;

    private JLabel lblCantidad;
    private JLabel lblPrecioTotal;
    private Runnable onCantidadChange; // Callback para actualizar subtotal global

    public ItemOrden(String nombre, double precio, Image imagenProducto, Runnable onCantidadChange) {
        this.nombreProducto = nombre;
        this.precioUnitario = precio;
        this.onCantidadChange = onCantidadChange;

        setOpaque(false);
        setLayout(new BorderLayout(10, 0));
        setPreferredSize(new Dimension(280, 55));
        setMaximumSize(new Dimension(320, 55));
        setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        // 1. Imagen del producto (Izquierda)
        JLabel lblImagen = new JLabel();
        if (imagenProducto != null) {
            Image imgEscalada = imagenProducto.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        }
        add(lblImagen, BorderLayout.WEST);

        // 2. Información del producto (Centro)
        JPanel panelCentro = new JPanel(new GridLayout(2, 1));
        panelCentro.setOpaque(false);

        JLabel lblNombre = new JLabel(nombreProducto);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(new Color(35, 35, 35));

        // Controles de cantidad (+ / -)
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panelControles.setOpaque(false);

        JButton btnMenos = crearBotonRedondo("-", new Color(230, 230, 230), Color.BLACK);
        lblCantidad = new JLabel("1");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCantidad.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));

        JButton btnMas = crearBotonRedondo("+", new Color(40, 167, 69), Color.WHITE); // Verde McDonald's

        btnMenos.addActionListener(e -> {
            if (cantidad > 1) {
                cantidad--;
                actualizarItem();
            }
        });

        btnMas.addActionListener(e -> {
            cantidad++;
            actualizarItem();
        });

        panelControles.add(btnMenos);
        panelControles.add(lblCantidad);
        panelControles.add(btnMas);

        panelCentro.add(lblNombre);
        panelCentro.add(panelControles);
        add(panelCentro, BorderLayout.CENTER);

        // 3. Precio Total y Eliminar (Derecha)
        JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 12));
        panelDerecha.setOpaque(false);

        lblPrecioTotal = new JLabel(String.format("Q %.2f", precioUnitario));
        lblPrecioTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPrecioTotal.setForeground(new Color(219, 0, 7)); // Rojo McDonald's

        JButton btnEliminar = new JButton("🗑");
        btnEliminar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar.addActionListener(e -> {
            Container parent = getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
                if (onCantidadChange != null) onCantidadChange.run();
            }
        });

        panelDerecha.add(lblPrecioTotal);
        panelDerecha.add(btnEliminar);
        add(panelDerecha, BorderLayout.EAST);
    }

    private void actualizarItem() {
        lblCantidad.setText(String.valueOf(cantidad));
        lblPrecioTotal.setText(String.format("Q %.2f", cantidad * precioUnitario));
        if (onCantidadChange != null) {
            onCantidadChange.run();
        }
    }

    private JButton crearBotonRedondo(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(22, 22));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}