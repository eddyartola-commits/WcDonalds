package Componentes;

import Modelo.Producto;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelDetalleProducto extends JPanel {

    private JLabel lblImagen;
    private JLabel lblNombre;
    private JLabel lblDescripcion;
    private JLabel lblPrecio;
    private JButton btnAgregarCarrito;
    private JPanel panelExtras;
    private JLabel lblCantidad;
    
    // Botones de Tamaño
    private JButton btnIndividual;
    private JButton btnAgrandado;
    
    private int cantidad = 1;
    private double costoTamanoExtra = 0.0; // 0.0 para Individual, 7.0 para Agrandado
    private Producto productoActual;

    // Colores McDonald's
    private final Color COLOR_AMARILLO = new Color(255, 188, 13);
    private final Color COLOR_ROJO = new Color(218, 41, 28);
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245);
    private final Color COLOR_GRIS_TEXTO = new Color(80, 80, 80);

    public PanelDetalleProducto() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);

        // 1. ESPACIO SUPERIOR MODERADO
        panelContenido.add(Box.createVerticalStrut(20));

        // 2. Imagen centrada
        lblImagen = new JLabel();
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenido.add(lblImagen);
        panelContenido.add(Box.createVerticalStrut(12));

        // 3. Nombre del producto
        lblNombre = new JLabel("Selecciona un producto", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial Black", Font.BOLD, 20));
        lblNombre.setForeground(new Color(20, 20, 20));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblNombre);
        panelContenido.add(Box.createVerticalStrut(6));

        // 4. Descripción
        lblDescripcion = new JLabel("<html><div style='text-align: center; width: 260px; color: #666666;'>"
                + "Selecciona un producto del menú para ver detalles.</div></html>", SwingConstants.CENTER);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblDescripcion);
        panelContenido.add(Box.createVerticalStrut(10));

        // 5. Precio Destacado
        lblPrecio = new JLabel("Q 0.00", SwingConstants.CENTER);
        lblPrecio.setFont(new Font("Arial Black", Font.BOLD, 26));
        lblPrecio.setForeground(COLOR_ROJO);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblPrecio);
        panelContenido.add(Box.createVerticalStrut(15));

        // 6. SECCIÓN TAMAÑO (Individual / Agrandado)
        JPanel panelSeccionTamano = crearSeccionTamano();
        panelSeccionTamano.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(panelSeccionTamano);
        
        // --- ESPACIO PRUDENTE Y LIMPIO ENTRE TAMAÑO Y EXTRAS (18px) ---
        panelContenido.add(Box.createVerticalStrut(18));

        // 7. EXTRAS OPCIONALES
        panelExtras = new JPanel();
        panelExtras.setLayout(new BoxLayout(panelExtras, BoxLayout.Y_AXIS));
        panelExtras.setOpaque(false);
        panelExtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(panelExtras);
        panelContenido.add(Box.createVerticalStrut(15));

        // 8. SELECTOR DE CANTIDAD (- 1 +)
        JPanel panelCantidad = crearControlCantidad();
        panelCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(panelCantidad);
        panelContenido.add(Box.createVerticalStrut(20));

        // 9. BOTÓN ROJO SUBIDO (Se agrega directamente al contenido vertical para quedar pegado arriba)
        btnAgregarCarrito = new JButton("Agregar al carrito") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled() ? COLOR_ROJO : new Color(210, 210, 210));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnAgregarCarrito.setFont(new Font("Arial Black", Font.BOLD, 15));
        btnAgregarCarrito.setForeground(Color.WHITE);
        btnAgregarCarrito.setContentAreaFilled(false);
        btnAgregarCarrito.setFocusPainted(false);
        btnAgregarCarrito.setBorderPainted(false);
        btnAgregarCarrito.setPreferredSize(new Dimension(270, 50));
        btnAgregarCarrito.setMaximumSize(new Dimension(270, 50));
        btnAgregarCarrito.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregarCarrito.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarCarrito.setEnabled(false);

        panelContenido.add(btnAgregarCarrito);
        panelContenido.add(Box.createVerticalStrut(15));

        JScrollPane scroll = new JScrollPane(panelContenido);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearSeccionTamano() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel lblTitulo = new JLabel("Tamaño", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 13));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(lblTitulo);
        p.add(Box.createVerticalStrut(6));

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(270, 40));
        panelBotones.setMaximumSize(new Dimension(270, 40));

        btnIndividual = crearBotonOpcionTamano("Individual", true);
        btnAgrandado = crearBotonOpcionTamano("Agrandado", false);

        btnIndividual.addActionListener(e -> seleccionarTamano(true));
        btnAgrandado.addActionListener(e -> seleccionarTamano(false));

        panelBotones.add(btnIndividual);
        panelBotones.add(btnAgrandado);

        p.add(panelBotones);
        return p;
    }

    private JButton crearBotonOpcionTamano(String texto, boolean seleccionado) {
        JButton b = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean esSeleccionado = (this == btnIndividual && costoTamanoExtra == 0.0)
                        || (this == btnAgrandado && costoTamanoExtra == 7.0);

                if (esSeleccionado) {
                    g2.setColor(COLOR_AMARILLO);
                } else {
                    g2.setColor(COLOR_GRIS_FONDO);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        b.setFont(new Font("Arial Black", Font.BOLD, 12));
        b.setForeground(seleccionado ? Color.BLACK : COLOR_GRIS_TEXTO);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void seleccionarTamano(boolean esIndividual) {
        if (esIndividual) {
            costoTamanoExtra = 0.0;
            btnIndividual.setForeground(Color.BLACK);
            btnAgrandado.setForeground(COLOR_GRIS_TEXTO);
        } else {
            costoTamanoExtra = 7.0;
            btnIndividual.setForeground(COLOR_GRIS_TEXTO);
            btnAgrandado.setForeground(Color.BLACK);
        }

        btnIndividual.repaint();
        btnAgrandado.repaint();
        actualizarPrecioTotal();
    }

    private JPanel crearControlCantidad() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        p.setOpaque(false);

        JButton btnMenos = crearBotonRedondo("-");
        JButton btnMas = crearBotonRedondo("+");

        lblCantidad = new JLabel("1", SwingConstants.CENTER);
        lblCantidad.setFont(new Font("Arial Black", Font.BOLD, 18));
        lblCantidad.setPreferredSize(new Dimension(25, 25));

        btnMenos.addActionListener(e -> {
            if (cantidad > 1) {
                cantidad--;
                lblCantidad.setText(String.valueOf(cantidad));
                actualizarPrecioTotal();
            }
        });

        btnMas.addActionListener(e -> {
            cantidad++;
            lblCantidad.setText(String.valueOf(cantidad));
            actualizarPrecioTotal();
        });

        p.add(btnMenos);
        p.add(lblCantidad);
        p.add(btnMas);
        return p;
    }

    private JButton crearBotonRedondo(String texto) {
        JButton b = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(238, 238, 238));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Arial Black", Font.BOLD, 16));
        b.setForeground(Color.BLACK);
        b.setPreferredSize(new Dimension(38, 38));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public void mostrarProducto(Producto p) {
     this.productoActual = p;
    this.cantidad = 1;
    lblCantidad.setText("1");

    seleccionarTamano(true);

    // Nombre del producto
    lblNombre.setText("<html><div style='text-align: center; width: 260px;'>" + p.getNombre() + "</div></html>");

    // DESCRIPCIÓN DESDE LA BASE DE DATOS
    String desc = (p.getDescripcion() != null && !p.getDescripcion().trim().isEmpty())
            ? p.getDescripcion().trim()
            : "Acompañado de los mejores ingredientes frescos.";

    // Formato HTML que ajusta automáticamente el texto largo al ancho del panel (260px)
    lblDescripcion.setText("<html><div style='text-align: center; width: 260px; color: #666666; font-family: Segoe UI; font-size: 10px;'>" 
            + desc + "</div></html>");

    // Cargar la imagen del producto
    String ruta = p.getImagenPath();
    if (ruta != null) {
        java.io.File archivo = new java.io.File(ruta.trim());
        if (archivo.exists()) {
            lblImagen.setIcon(Modelo.MoldeProductos.escalarImagenRapida(archivo, 240, 180));
        }
    }

    construirSeccionExtras();
    actualizarPrecioTotal();

    btnAgregarCarrito.setEnabled(true);
    revalidate();
    repaint();
    }

    private void actualizarPrecioTotal() {
        if (productoActual != null) {
            double precioBaseConTamano = productoActual.getPrecio() + costoTamanoExtra;
            double totalFinal = precioBaseConTamano * cantidad;

            lblPrecio.setText(String.format("Q %.2f", precioBaseConTamano));
            btnAgregarCarrito.setText(String.format("Agregar al carrito  •  Q %.2f", totalFinal));
        }
    }

    private void construirSeccionExtras() {
     panelExtras.removeAll();

    // Validar si el producto es una hamburguesa (por ID de categoría o por su nombre)
    // Asumiendo que la categoría 1 corresponde a Hamburguesas, o evaluando si el nombre lo dice:
    boolean esHamburguesa = false;

    if (productoActual != null) {
        // Opción A: Por ID de categoría (Ajusta '1' al ID real de la categoría Hamburguesas en tu BD)
        esHamburguesa = (productoActual.getIdCategoria() == 1);

        // Opción B (Respaldar por nombre si no estás seguro del ID)
        if (!esHamburguesa && productoActual.getNombre() != null) {
            String nombreUpper = productoActual.getNombre().toUpperCase();
            esHamburguesa = nombreUpper.contains("HAMBURGUESA") 
                         || nombreUpper.contains("BURGER") 
                         || nombreUpper.contains("TOCINO")
                         || nombreUpper.contains("MCCHEESE");
        }
    }

    // SI NO ES HAMBURGUESA, NO DIBUJAR NADA Y OCULTAR EL PANEL
    if (!esHamburguesa) {
        panelExtras.setVisible(false);
        panelExtras.revalidate();
        panelExtras.repaint();
        return;
    }

    // SI ES HAMBURGUESA, MOSTRAR LOS EXTRAS
    panelExtras.setVisible(true);
    panelExtras.setPreferredSize(new Dimension(270, 100));
    panelExtras.setMaximumSize(new Dimension(270, 120));

    JLabel lblTituloExtras = new JLabel("Extras opcionales", SwingConstants.CENTER);
    lblTituloExtras.setFont(new Font("Arial Black", Font.BOLD, 13));
    lblTituloExtras.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelExtras.add(lblTituloExtras);
    panelExtras.add(Box.createVerticalStrut(8));

    String[][] extras = {
        {"Queso Extra", "5.00"},
        {"Tocino Crujiente", "6.00"},
        {"Salsa Especial", "2.00"}
    };

    for (String[] ext : extras) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(270, 24));

        JCheckBox chk = new JCheckBox(ext[0]);
        chk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chk.setOpaque(false);

        JLabel lblPrecioExtra = new JLabel("+ Q " + ext[1]);
        lblPrecioExtra.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPrecioExtra.setForeground(COLOR_GRIS_TEXTO);

        fila.add(chk, BorderLayout.WEST);
        fila.add(lblPrecioExtra, BorderLayout.EAST);

        panelExtras.add(fila);
        panelExtras.add(Box.createVerticalStrut(4));
    }

    panelExtras.revalidate();
    panelExtras.repaint();
    }

    public void limpiarListenersAgregar() {
        for (java.awt.event.ActionListener al : btnAgregarCarrito.getActionListeners()) {
            btnAgregarCarrito.removeActionListener(al);
        }
    }

    public JButton getBtnAgregarCarrito() {
        return btnAgregarCarrito;
    }

    public Producto getProductoActual() {
        return productoActual;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getCostoTamanoExtra() {
        return costoTamanoExtra;
    }
}