package Componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Línea visual del carrito. Conserva el id del producto para poder registrar
 * correctamente detalle_pedido al pasar a la pantalla de pagos.
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

    // Constructor compatible con el código antiguo.
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
        setLayout(new BorderLayout(10, 0));
        setPreferredSize(new Dimension(290, 62));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(48, 48));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        if (imagenProducto != null) {
            Image imgEscalada = imagenProducto.getScaledInstance(44, 44, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        }
        add(lblImagen, BorderLayout.WEST);

        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(nombreProducto);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(new Color(35, 35, 35));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 1));
        panelControles.setOpaque(false);
        panelControles.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnMenos = crearBotonRedondo("-", new Color(235, 235, 235), Color.BLACK);
        lblCantidad = new JLabel("1");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCantidad.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        JButton btnMas = crearBotonRedondo("+", new Color(34, 139, 75), Color.WHITE);

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

        JPanel panelDerecha = new JPanel();
        panelDerecha.setOpaque(false);
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

        JButton btnEliminar = new JButton("×");
        btnEliminar.setToolTipText("Eliminar producto");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnEliminar.setForeground(new Color(120, 120, 120));
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        lblPrecioTotal = new JLabel(String.format("Q %.2f", precioUnitario));
        lblPrecioTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPrecioTotal.setForeground(new Color(205, 0, 20));
        lblPrecioTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        btnEliminar.addActionListener(e -> {
            Container parent = getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
                if (onCantidadChange != null) onCantidadChange.run();
            }
        });

        panelDerecha.add(btnEliminar);
        panelDerecha.add(Box.createVerticalGlue());
        panelDerecha.add(lblPrecioTotal);
        add(panelDerecha, BorderLayout.EAST);
    }

    private void actualizarItem() {
        lblCantidad.setText(String.valueOf(cantidad));
        lblPrecioTotal.setText(String.format("Q %.2f", cantidad * precioUnitario));
        if (onCantidadChange != null) onCantidadChange.run();
    }

    private JButton crearBotonRedondo(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
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

    public int getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }
    public String getImagenPath() { return imagenPath; }
    public double getSubtotal() { return cantidad * precioUnitario; }
}
