package Componentes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BotonPerfilCajero extends JPanel {

    private String textoRol = "Cajero";
    private JWindow ventanaMenu;
    private JPanel panelOpcionCerrar;
    private JFrame ventanaPadre;

    // Colores McDonald's
    private final Color AMARILLO_MCDONALDS = new Color(255, 188, 13);
    private final Color AMARILLO_PRESS = new Color(230, 165, 0);
    private final Color ROJO_MCDONALDS = new Color(218, 41, 28);
    private final Color ROJO_TEXTO = new Color(180, 20, 15);

    // Animación del Menú Flotante
    private Timer timerAnimacion;
    private float progresoAnimacion = 0.0f; // 0.0f (oculto) a 1.0f (desplegado)

    // Estados de Hover e Interacción
    private boolean estaHoverBoton = false;
    private AWTEventListener listenerGlobalClic;

    public BotonPerfilCajero() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(170, 38));

        // Eventos del Botón Principal
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
                // Alternar: si ya está visible, se oculta; si no, se muestra
                if (ventanaMenu != null && ventanaMenu.isVisible()) {
                    ocultarMenuConAnimacion();
                } else {
                    mostrarMenuDesplegable();
                }
            }
        });

        // Crear Menú Flotante sin bordes nativos usando JWindow
        crearVentanaFlotante();
        registrarListenerGlobalParaCerrar();
    }

    private void crearVentanaFlotante() {
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
                            ocultarMenuConAnimacion();
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
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // DIBUJO DEL TRIÁNGULO SUPERIOR APUNTANDO AL BOTÓN
                int topOffset = (int) (10 * progresoAnimacion);
                if (topOffset < 1) topOffset = 1;

                int[] xPuntos = {w / 2 - 8, w / 2, w / 2 + 8};
                int[] yPuntos = {topOffset, 1, topOffset};

                g2.setColor(AMARILLO_MCDONALDS);
                g2.fillPolygon(xPuntos, yPuntos, 3);

                // ANIMACIÓN DE EXPANSIÓN Y OPACIDAD DE APARICIÓN
                float alpha = Math.min(1.0f, Math.max(0.0f, progresoAnimacion));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                int padding = estaHoverOption ? 0 : 3;
                int hBoton = h - 10 - (padding * 2);
                int wBoton = w - (padding * 2);
                int yBoton = 10 + padding;
                int xBoton = padding;

                Color colorRojoBoton = ROJO_MCDONALDS;
                Color colorBordeBoton = AMARILLO_MCDONALDS;

                if (estaClickOption) {
                    colorRojoBoton = new Color(170, 20, 15);
                    colorBordeBoton = AMARILLO_PRESS;
                } else if (estaHoverOption) {
                    colorRojoBoton = new Color(235, 45, 30);
                    colorBordeBoton = Color.WHITE;
                }

                // Cápsula Exterior
                g2.setColor(colorBordeBoton);
                g2.fillRoundRect(xBoton, yBoton, wBoton, hBoton, 22, 22);

                // Cápsula Interior Roja
                g2.setColor(colorRojoBoton);
                g2.fillRoundRect(xBoton + 4, yBoton + 4, wBoton - 8, hBoton - 8, 16, 16);

                // DIBUJO DEL ÍCONO DE PUERTA + FLECHA
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int iconX = xBoton + 18;
                int iconY = yBoton + (hBoton / 2);

                // Arco de la puerta
                g2.drawArc(iconX - 6, iconY - 8, 12, 16, 90, 180);
                g2.drawLine(iconX, iconY - 8, iconX + 3, iconY - 8);
                g2.drawLine(iconX, iconY + 8, iconX + 3, iconY + 8);

                // Flecha de salida
                g2.drawLine(iconX - 2, iconY, iconX + 8, iconY);
                g2.drawLine(iconX + 4, iconY - 4, iconX + 8, iconY);
                g2.drawLine(iconX + 4, iconY + 4, iconX + 8, iconY);

                // LÍNEA SEPARADORA VERTICAL
                g2.setColor(new Color(255, 255, 255, 90));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawLine(iconX + 16, yBoton + 8, iconX + 16, yBoton + hBoton - 8);

                // TEXTO "Cerrar sesión" CENTRADO VERTICALMENTE
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int textY = yBoton + ((hBoton - fm.getHeight()) / 2) + fm.getAscent();

                g2.drawString("Cerrar sesión", iconX + 26, textY);

                g2.dispose();
            }
        };

        panelOpcionCerrar.setOpaque(false);
        panelOpcionCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelOpcionCerrar.setPreferredSize(new Dimension(210, 58));

        ventanaMenu.setContentPane(panelOpcionCerrar);
        ventanaMenu.pack();

        ventanaMenu.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                ocultarMenuConAnimacion();
            }
        });
    }

    // LISTENER GLOBAL PARA CAPTURAR CLICS EN CUALQUIER PARTE DE LA PANTALLA
    private void registrarListenerGlobalParaCerrar() {
        listenerGlobalClic = event -> {
            if (event instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) event;
                if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                    if (ventanaMenu != null && ventanaMenu.isVisible()) {
                        Point puntoClic = me.getLocationOnScreen();

                        boolean clicEnBotonPrincipal = containsScreenPoint(BotonPerfilCajero.this, puntoClic);
                        boolean clicEnVentanaMenu = containsScreenPoint(ventanaMenu, puntoClic);

                        if (!clicEnBotonPrincipal && !clicEnVentanaMenu) {
                            ocultarMenuConAnimacion();
                        }
                    }
                }
            }
        };

        Toolkit.getDefaultToolkit().addAWTEventListener(listenerGlobalClic, AWTEvent.MOUSE_EVENT_MASK);
    }

    private boolean containsScreenPoint(Component comp, Point screenPoint) {
        if (comp == null || !comp.isShowing()) return false;
        Point loc = comp.getLocationOnScreen();
        Rectangle bounds = new Rectangle(loc.x, loc.y, comp.getWidth(), comp.getHeight());
        return bounds.contains(screenPoint);
    }

    private void mostrarMenuDesplegable() {
        if (ventanaMenu == null) {
            crearVentanaFlotante();
        }

        Point loc = getLocationOnScreen();
        int x = loc.x + (getWidth() - 210) / 2;
        int y = loc.y + getHeight() - 2;

        ventanaMenu.setLocation(x, y);
        ventanaMenu.setVisible(true);
        ventanaMenu.toFront();

        // Iniciar Animación de Entrada Suave (60 FPS)
        if (timerAnimacion != null && timerAnimacion.isRunning()) {
            timerAnimacion.stop();
        }

        progresoAnimacion = 0.0f;
        timerAnimacion = new Timer(12, e -> {
            progresoAnimacion += 0.15f;
            if (progresoAnimacion >= 1.0f) {
                progresoAnimacion = 1.0f;
                timerAnimacion.stop();
            }
            panelOpcionCerrar.repaint();
        });
        timerAnimacion.start();
    }

    private void ocultarMenuConAnimacion() {
        if (ventanaMenu != null && ventanaMenu.isVisible()) {
            if (timerAnimacion != null && timerAnimacion.isRunning()) {
                timerAnimacion.stop();
            }

            timerAnimacion = new Timer(10, e -> {
                progresoAnimacion -= 0.18f;
                if (progresoAnimacion <= 0.0f) {
                    progresoAnimacion = 0.0f;
                    ventanaMenu.setVisible(false);
                    timerAnimacion.stop();
                }
                panelOpcionCerrar.repaint();
            });
            timerAnimacion.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        int margin = estaHoverBoton ? 0 : 2;
        int x = margin;
        int y = margin;
        int w = ancho - (margin * 2);
        int h = alto - (margin * 2);

        // Dibujar Fondo Cápsula
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

        // --- CÁLCULO DE CENTRADO EXACTO DEL TEXTO "Cajero" + FLECHA ---
        Font fontTexto = new Font("Arial Black", Font.BOLD, 13);
        Font fontFlecha = new Font("Segoe UI", Font.PLAIN, 10);

        g2.setFont(fontTexto);
        FontMetrics fmTexto = g2.getFontMetrics(fontTexto);
        FontMetrics fmFlecha = g2.getFontMetrics(fontFlecha);

        String flecha = "▼";
        int espacioEntre = 6;
        int anchoTotal = fmTexto.stringWidth(textoRol) + espacioEntre + fmFlecha.stringWidth(flecha);

        int startX = (ancho - anchoTotal) / 2;
        int textY = y + ((h - fmTexto.getHeight()) / 2) + fmTexto.getAscent();

        // Dibujar Texto del Rol ("Cajero")
        g2.setColor(ROJO_TEXTO);
        g2.drawString(textoRol, startX, textY);

        // Dibujar Flecha Pequeña
        g2.setFont(fontFlecha);
        g2.setColor(ROJO_MCDONALDS);
        g2.drawString(flecha, startX + fmTexto.stringWidth(textoRol) + espacioEntre, textY - 1);

        g2.dispose();
    }

    public void setRol(String rol) {
        this.textoRol = rol;
        repaint();
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