package Componentes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Tabla visual para historial de pedidos WcDonald's.
 * Columnas alineadas mediante el mismo layout proporcional
 * para encabezado y todas las filas.
 */
public class TablaHistorial extends JPanel {

    private final JPanel panelFilas;
    private final List<FilaHistorial> filas = new ArrayList<>();

    private static final double[] COLUMNAS = {
        0.08, // Hora
        0.09, // Pedido
        0.17, // Cliente
        0.18, // Método
        0.10, // Total
        0.15, // Estado
        0.23  // Acciones
    };

    private final Color COLOR_TEXTO = new Color(35, 60, 92);
    private final Color COLOR_SECUNDARIO = new Color(79, 103, 133);
    private final Color COLOR_BORDE = new Color(229, 234, 240);
    private final Color COLOR_HEADER = new Color(255, 244, 245);

    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font FUENTE_BOLD = new Font("Segoe UI", Font.BOLD, 12);

    public TablaHistorial() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(new RoundedBorder(COLOR_BORDE, 1, 14));

        JPanel encabezado = crearEncabezado();

        panelFilas = new JPanel();
        panelFilas.setBackground(Color.WHITE);
        panelFilas.setLayout(new BoxLayout(panelFilas, BoxLayout.Y_AXIS));

        contenedor.add(encabezado, BorderLayout.NORTH);
        contenedor.add(panelFilas, BorderLayout.CENTER);
        add(contenedor, BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new ColumnLayout(COLUMNAS));
        header.setBackground(COLOR_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        header.setPreferredSize(new Dimension(900, 58));
        header.setMinimumSize(new Dimension(700, 58));

        header.add(crearHeader("Hora", SwingConstants.LEFT));
        header.add(crearHeader("Pedido", SwingConstants.CENTER));
        header.add(crearHeader("Cliente", SwingConstants.LEFT));
        header.add(crearHeader("Método de pago", SwingConstants.LEFT));
        header.add(crearHeader("Total", SwingConstants.CENTER));
        header.add(crearHeader("Estado", SwingConstants.CENTER));
        header.add(crearHeader("Acciones", SwingConstants.CENTER));

        return header;
    }

    private JLabel crearHeader(String texto, int alineacion) {
        JLabel label = new JLabel(texto);
        label.setForeground(new Color(30, 47, 72));
        label.setFont(FUENTE_BOLD);
        label.setHorizontalAlignment(alineacion);
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        return label;
    }

    public void agregarFila(String hora, String pedido, String cliente,
            String metodo, String total, String estado) {

        FilaHistorial fila = new FilaHistorial(
                hora, pedido, cliente, metodo, total, estado);

        filas.add(fila);
        panelFilas.add(fila);
        panelFilas.revalidate();
        panelFilas.repaint();
    }

    public void limpiarTabla() {
        filas.clear();
        panelFilas.removeAll();
        panelFilas.revalidate();
        panelFilas.repaint();
    }

    private class FilaHistorial extends JPanel {

        private final String pedido;

        public FilaHistorial(String hora, String pedido, String cliente,
                String metodo, String total, String estado) {

            this.pedido = pedido;

            setBackground(Color.WHITE);
            setLayout(new ColumnLayout(COLUMNAS));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));
            setPreferredSize(new Dimension(900, 66));
            setMinimumSize(new Dimension(700, 66));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE),
                    BorderFactory.createEmptyBorder(0, 8, 0, 8)
            ));

            add(crearTexto(hora, false, SwingConstants.LEFT));
            add(crearTexto(pedido, true, SwingConstants.CENTER));
            add(crearTexto(cliente, false, SwingConstants.LEFT));
            add(crearMetodo(metodo));
            add(crearTexto(total, true, SwingConstants.CENTER));
            add(crearEstado(estado));
            add(crearAcciones());

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(249, 251, 253));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.WHITE);
                }
            });
        }

        private JLabel crearTexto(String texto, boolean bold, int alineacion) {
            JLabel label = new JLabel(texto);
            label.setForeground(COLOR_TEXTO);
            label.setFont(bold ? FUENTE_BOLD : FUENTE_NORMAL);
            label.setHorizontalAlignment(alineacion);
            label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
            return label;
        }

        private JPanel crearMetodo(String metodo) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 0));
            panel.setOpaque(false);

            JLabel icono = new JLabel();
            icono.setFont(new Font("Segoe UI", Font.BOLD, 15));
            icono.setPreferredSize(new Dimension(20, 28));
            icono.setHorizontalAlignment(SwingConstants.CENTER);

            if ("Efectivo".equalsIgnoreCase(metodo)) {
                icono.setText("$");
                icono.setForeground(new Color(25, 150, 85));
            } else if ("Tarjeta".equalsIgnoreCase(metodo)) {
                icono.setText("T");
                icono.setForeground(new Color(35, 70, 110));
            } else if ("QR".equalsIgnoreCase(metodo)) {
                icono.setText("QR");
                icono.setFont(new Font("Segoe UI", Font.BOLD, 10));
                icono.setForeground(new Color(35, 70, 110));
            } else {
                icono.setText("•");
                icono.setForeground(COLOR_SECUNDARIO);
            }

            JLabel texto = new JLabel(metodo == null ? "" : metodo.toUpperCase());
            texto.setForeground(COLOR_SECUNDARIO);
            texto.setFont(FUENTE_NORMAL);

            panel.add(icono);
            panel.add(texto);
            return panel;
        }

        private JPanel crearEstado(String estado) {
            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setOpaque(false);

            boolean completada = "Completada".equalsIgnoreCase(estado);
            EstadoBadge badge = new EstadoBadge(estado, completada);
            wrapper.add(badge);
            return wrapper;
        }

        private JPanel crearAcciones() {
            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 7, 0));
            acciones.setOpaque(false);

            JButton detalles = crearBotonAccion("Detalles", 88);
            JButton imprimir = crearBotonAccion("Reimprimir", 105);

            detalles.addActionListener(e -> eventoDetalles(pedido));
            imprimir.addActionListener(e -> eventoReimprimir(pedido));

            acciones.add(detalles);
            acciones.add(imprimir);
            return acciones;
        }
    }

    private JButton crearBotonAccion(String texto, int ancho) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        boton.setForeground(new Color(50, 75, 108));
        boton.setBackground(new Color(250, 252, 254));
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new RoundedBorder(new Color(220, 227, 235), 1, 10));
        boton.setPreferredSize(new Dimension(ancho, 34));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(244, 247, 250));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(250, 252, 254));
            }
        });

        return boton;
    }

    protected void eventoDetalles(String pedido) {
        JOptionPane.showMessageDialog(
                this,
                "Detalles del pedido " + pedido,
                "Detalles",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    protected void eventoReimprimir(String pedido) {
        JOptionPane.showMessageDialog(
                this,
                "Reimprimir pedido " + pedido,
                "Reimprimir",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private class EstadoBadge extends JLabel {

        private final boolean completada;

        public EstadoBadge(String texto, boolean completada) {
            super(texto);
            this.completada = completada;

            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(108, 28));
            setForeground(completada
                    ? new Color(28, 125, 75)
                    : new Color(205, 55, 70));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(completada
                    ? new Color(220, 248, 230)
                    : new Color(255, 226, 231));

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Layout compartido por encabezado y filas.
     * Esto hace que cada columna empiece y termine exactamente
     * en la misma posición sin importar el contenido.
     */
    private static class ColumnLayout implements LayoutManager {

        private final double[] pesos;

        public ColumnLayout(double[] pesos) {
            this.pesos = pesos.clone();
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Insets i = parent.getInsets();
            return new Dimension(900 + i.left + i.right, 66 + i.top + i.bottom);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Insets i = parent.getInsets();
            return new Dimension(700 + i.left + i.right, 50 + i.top + i.bottom);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int ancho = parent.getWidth() - insets.left - insets.right;
            int alto = parent.getHeight() - insets.top - insets.bottom;

            Component[] componentes = parent.getComponents();
            int x = insets.left;
            int usados = 0;

            for (int n = 0; n < componentes.length && n < pesos.length; n++) {
                int w;

                if (n == pesos.length - 1) {
                    w = ancho - usados;
                } else {
                    w = (int) Math.round(ancho * pesos[n]);
                }

                componentes[n].setBounds(x, insets.top, Math.max(0, w), alto);
                x += w;
                usados += w;
            }
        }
    }

    private static class RoundedBorder extends AbstractBorder {

        private final Color color;
        private final int thickness;
        private final int radius;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g,
                int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(
                    x, y, width - 1, height - 1,
                    radius, radius);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }
}
