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
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
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

    // Caché estático de imágenes en RAM
    private static final java.util.Map<String, ImageIcon> CACHE_IMAGENES = new java.util.HashMap<>();

    public MoldeProductos() {
        initComponents();
        configurarHoverYClick();
    }

    public static void guardarEnCache(String ruta, ImageIcon icono) {
        CACHE_IMAGENES.put(ruta, icono);
    }

    public static boolean existeEnCache(String ruta) {
        return CACHE_IMAGENES.containsKey(ruta);
    }

    public static ImageIcon obtenerDeCache(String ruta) {
        return CACHE_IMAGENES.get(ruta);
    }

    // Escalado de imagen ultra rápido con Graphics2D (Evita congelamientos)
    public static ImageIcon escalarImagenRapida(File archivo, int ancho, int alto) {
        try {
            BufferedImage imgOriginal = ImageIO.read(archivo);
            if (imgOriginal == null) return null;

            BufferedImage imgResized = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imgResized.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.drawImage(imgOriginal, 0, 0, ancho, alto, null);
            g2.dispose();

            return new ImageIcon(imgResized);
        } catch (Exception e) {
            return null;
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        Dimension tamanoFijo = new Dimension(210, 250);
        setPreferredSize(tamanoFijo);
        setMinimumSize(tamanoFijo);
        setMaximumSize(tamanoFijo);
        setSize(tamanoFijo);

        setBackground(COLOR_NORMAL);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 12, 10, 12));

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);

        lblNombre = new JLabel("Producto");
        lblNombre.setFont(new Font("Arial Black", Font.BOLD, 12));
        lblNombre.setForeground(new Color(20, 20, 20));
        lblNombre.setBorder(new EmptyBorder(6, 0, 4, 0));

        JPanel panelFilaPrecio = new JPanel(new BorderLayout());
        panelFilaPrecio.setOpaque(false);

        lblPrecio = new JLabel("Q 0.00");
        lblPrecio.setFont(new Font("Arial Black", Font.BOLD, 14));
        lblPrecio.setForeground(BTN_ROJO_NORMAL);

        btnAgregar = new JButton("+") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                if (getModel().isPressed()) {
                    g2.scale(0.9, 0.9);
                    g2.translate(w * 0.05, h * 0.05);
                    g2.setColor(BTN_ROJO_CLICK);
                } else {
                    g2.setColor(BTN_ROJO_NORMAL);
                }

                g2.fillRoundRect(0, 0, w, h, 14, 14);
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
        btnAgregar.setPreferredSize(new Dimension(36, 36));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFilaPrecio.add(lblPrecio, BorderLayout.WEST);
        panelFilaPrecio.add(btnAgregar, BorderLayout.EAST);

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
            public void mouseClicked(MouseEvent e) {
                // Buscar el menú padre WcMenu
                MenuCajero.WcMenu menu = (MenuCajero.WcMenu) javax.swing.SwingUtilities.getAncestorOfClass(MenuCajero.WcMenu.class, MoldeProductos.this);
                if (menu != null && producto != null) {
                    menu.actualizarDetalleDerecho(producto);
                }
            }
    
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
        lblNombre.setText("<html><div style='width: 140px; text-overflow: ellipsis;'>" + p.getNombre() + "</div></html>");
        lblPrecio.setText("Q " + String.format("%.2f", p.getPrecio()));

        String ruta = p.getImagenPath();

        if (ruta != null && !ruta.trim().isEmpty()) {
            String rutaLimpia = ruta.trim();

            if (CACHE_IMAGENES.containsKey(rutaLimpia)) {
                lblImagen.setIcon(CACHE_IMAGENES.get(rutaLimpia));
            } else {
                File archivo = new File(rutaLimpia);
                if (archivo.exists()) {
                    ImageIcon iconoRapido = escalarImagenRapida(archivo, 175, 125);
                    if (iconoRapido != null) {
                        CACHE_IMAGENES.put(rutaLimpia, iconoRapido);
                        lblImagen.setIcon(iconoRapido);
                    }
                }
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