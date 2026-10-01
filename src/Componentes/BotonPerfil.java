package Componentes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BotonPerfil extends JPanel {

    private JLabel lblRol;
    private JLabel lblFlecha;
    private JWindow ventanaMenu; // En lugar de JPopupMenu para cero bordes
    private JPanel panelOpcionCerrar;
    private JFrame ventanaPadre;

    // Colores McDonald's
    private final Color AMARILLO_MCDONALDS = new Color(255, 188, 13);
    private final Color AMARILLO_PRESS = new Color(230, 165, 0);
    private final Color ROJO_MCDONALDS = new Color(218, 41, 28);
    private final Color ROJO_TEXTO = new Color(180, 20, 15);

    // Estados de animación
    private boolean estaHoverBoton = false;

    public BotonPerfil() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 8, 6));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(160, 38));

        // 1. Botón Principal "Administrador"
        lblRol = new JLabel("Administrador");
        lblRol.setFont(new Font("Arial Black", Font.BOLD, 13));
        lblRol.setForeground(ROJO_TEXTO);

        lblFlecha = new JLabel("<html>&#9660;</html>");
        lblFlecha.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFlecha.setForeground(ROJO_MCDONALDS);

        add(lblRol);
        add(lblFlecha);

        // 2. Eventos del Botón Principal (Hover con escalado)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                estaHoverBoton = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                estaHoverBoton = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMenuDesplegable();
            }
        });

        // 3. Crear el Menú Flotante sin bordes nativos usando JWindow
        crearVentanaFlotante();
    }

    private void crearVentanaFlotante() {
        // Obtenemos la ventana de nivel superior
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        ventanaMenu = new JWindow(parentWindow);
        ventanaMenu.setBackground(new Color(0, 0, 0, 0)); // Transparencia total limpia

        panelOpcionCerrar = new JPanel() {
            private boolean estaHoverOption = false;
            private boolean estaClickOption = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        estaHoverOption = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        estaHoverOption = false;
                        estaClickOption = false;
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        estaClickOption = true;
                        repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (estaClickOption && estaHoverOption) {
                            ventanaMenu.setVisible(false);
                            ejecutarCerrarSesion();
                        }
                        estaClickOption = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // DIBUJO DEL PICO / TRIÁNGULO SUPERIOR
                int topOffset = 10;
                int[] xPuntos = {w / 2 - 8, w / 2, w / 2 + 8};
                int[] yPuntos = {topOffset, 1, topOffset};

                g2.setColor(AMARILLO_MCDONALDS);
                g2.fillPolygon(xPuntos, yPuntos, 3);

                // ANIMACIÓN SOBRESALIR (Crece en el Hover)
                int padding = estaHoverOption ? 0 : 3; // Al hacer hover se expande eliminando el margen interno
                int hBoton = h - topOffset - (padding * 2);
                int wBoton = w - (padding * 2);
                int yBoton = topOffset + padding;
                int xBoton = padding;

                Color colorRojoBoton = ROJO_MCDONALDS;
                Color colorBordeBoton = AMARILLO_MCDONALDS;

                if (estaClickOption) {
                    colorRojoBoton = new Color(170, 20, 15);
                    colorBordeBoton = AMARILLO_PRESS;
                } else if (estaHoverOption) {
                    colorRojoBoton = new Color(235, 45, 30); // Rojo más vivo
                    colorBordeBoton = Color.WHITE; // Resplandor blanco
                }

                // Dibuja Cápsula Exterior (Borde Amarillo/Blanco)
                g2.setColor(colorBordeBoton);
                g2.fillRoundRect(xBoton, yBoton, wBoton, hBoton, 22, 22);

                // Dibuja Cápsula Interior Roja
                g2.setColor(colorRojoBoton);
                g2.fillRoundRect(xBoton + 4, yBoton + 4, wBoton - 8, hBoton - 8, 16, 16);

                // DIBUJO DEL ÍCONO DE PUERTA + FLECHA EN BLANCO
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int iconX = xBoton + 22;
                int iconY = yBoton + (hBoton / 2);

                // Arco de la puerta
                g2.drawArc(iconX - 8, iconY - 9, 14, 18, 90, 180);
                g2.drawLine(iconX - 1, iconY - 9, iconX + 3, iconY - 9);
                g2.drawLine(iconX - 1, iconY + 9, iconX + 3, iconY + 9);

                // Flecha de salida
                g2.drawLine(iconX - 3, iconY, iconX + 9, iconY);
                g2.drawLine(iconX + 5, iconY - 4, iconX + 9, iconY);
                g2.drawLine(iconX + 5, iconY + 4, iconX + 9, iconY);

                // LÍNEA SEPARADORA VERTICAL
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(iconX + 18, yBoton + 10, iconX + 18, yBoton + hBoton - 10);

                // TEXTO "Cerrar sesión"
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial Black", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                int textY = yBoton + ((hBoton - fm.getHeight()) / 2) + fm.getAscent();

                g2.drawString("Cerrar sesión", iconX + 30, textY);

                g2.dispose();
            }
        };

        panelOpcionCerrar.setOpaque(false);
        panelOpcionCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelOpcionCerrar.setPreferredSize(new Dimension(230, 60));

        ventanaMenu.setContentPane(panelOpcionCerrar);
        ventanaMenu.pack();

        // Ocultar si la ventana pierde el foco
        ventanaMenu.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                ventanaMenu.setVisible(false);
            }
        });
    }

    private void mostrarMenuDesplegable() {
        if (ventanaMenu == null) {
            crearVentanaFlotante();
        }

        Point loc = getLocationOnScreen();
        int x = loc.x + (getWidth() - 230) / 2;
        int y = loc.y + getHeight() - 2;

        ventanaMenu.setLocation(x, y);
        ventanaMenu.setVisible(true);
        ventanaMenu.toFront();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // ANIMACIÓN DE SOBRESALIR EN EL BOTÓN PRINCIPAL
        int margin = estaHoverBoton ? 0 : 2; // Al hacer hover se expande 2px en los 4 lados
        int x = margin;
        int y = margin;
        int w = ancho - (margin * 2);
        int h = alto - (margin * 2);

        if (estaHoverBoton) {
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, w, h, 24, 24);
            g2.setColor(AMARILLO_MCDONALDS);
            g2.fillRoundRect(x + 2, y + 2, w - 4, h - 4, 22, 22);
        } else {
            g2.setColor(AMARILLO_MCDONALDS);
            g2.fillRoundRect(x, y, w, h, 24, 24);
        }

        g2.setColor(new Color(240, 170, 0));
        g2.drawRoundRect(x, y, w - 1, h - 1, 24, 24);

        g2.dispose();
        super.paintComponent(g);
    }

    public void setVentanaPadre(JFrame ventana) {
        this.ventanaPadre = ventana;
    }

    private void ejecutarCerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar la sesión y volver al inicio?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            } else {
                Window parent = SwingUtilities.getWindowAncestor(this);
                if (parent != null) {
                    parent.dispose();
                }
            }

            GUI.Login1 login = new GUI.Login1();
            login.setVisible(true);
        }
    }
}