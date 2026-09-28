package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class BotonAdmi extends JButton implements Serializable {

    // Paleta de Colores
    private Color activeBgColor = Color.decode("#FFBC0D");      // Amarillo brillante McDonald's
    private Color activeTextColor = Color.decode("#1A1A1A");    // Texto negro para el botón activo
    private Color inactiveTextColor = Color.WHITE;             // Texto blanco para botones inactivos

    // Animación
    private float escala = 1.0f;
    private float escalaObjetivo = 1.0f;
    private float progresoHover = 0.0f;
    private float progresoHoverObjetivo = 0.0f;
    private Timer animacion;

    public BotonAdmi() {
        this("POS");
    }

    public BotonAdmi(String text) {
        super(text);

        // Limpieza de propiedades por defecto
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        // Tipografía y alineación
        setFont(new Font("Segoe UI", Font.BOLD, 17));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(140, 48));
        setHorizontalAlignment(SwingConstants.CENTER);
        setIconTextGap(8);

        // Timer de animación a 60 FPS
        animacion = new Timer(15, e -> {
            boolean necesitaRepaint = false;

            // Transición de Escala (Zoom)
            if (Math.abs(escala - escalaObjetivo) > 0.001f) {
                escala += (escalaObjetivo - escala) * 0.20f;
                necesitaRepaint = true;
            } else {
                escala = escalaObjetivo;
            }

            // Transición de Hover
            if (Math.abs(progresoHover - progresoHoverObjetivo) > 0.01f) {
                progresoHover += (progresoHoverObjetivo - progresoHover) * 0.18f;
                necesitaRepaint = true;
            } else {
                progresoHover = progresoHoverObjetivo;
            }

            if (necesitaRepaint) {
                repaint();
            } else {
                animacion.stop();
            }
        });

        // Evento para desmarcar automáticamente los demás botones del mismo panel
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getParent() != null) {
                    for (Component c : getParent().getComponents()) {
                        if (c instanceof BotonAdmi) {
                            ((BotonAdmi) c).setSelected(false);
                        }
                    }
                }
                setSelected(true);
            }
        });

        // Eventos de Mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                escalaObjetivo = 1.04f;
                progresoHoverObjetivo = 1.0f;
                if (!animacion.isRunning()) animacion.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                escalaObjetivo = 1.0f;
                progresoHoverObjetivo = 0.0f;
                if (!animacion.isRunning()) animacion.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                escalaObjetivo = 0.95f;
                if (!animacion.isRunning()) animacion.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                escalaObjetivo = contains(e.getPoint()) ? 1.04f : 1.0f;
                if (!animacion.isRunning()) animacion.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Suavizado para bordes e íconos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Aplicar escala animada
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        g2.scale(escala, escala);
        g2.translate((centerX / escala) - centerX, (centerY / escala) - centerY);

        int marginX = 2;
        int marginY = 2;
        int arc = height - (marginY * 2); // Cápsula redondeada completa (Pill Shape)

        Shape forma = new RoundRectangle2D.Float(marginX, marginY, width - (marginX * 2), height - (marginY * 2), arc, arc);

        if (isSelected()) {
            // --- ESTADO ACTIVO (Seleccionado) ---
            setForeground(activeTextColor);

            // Sombra ambiental suave bajo la cápsula amarilla
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(marginX, marginY + 2, width - (marginX * 2), height - (marginY * 2), arc, arc);

            // Fondo amarillo cápsula
            g2.setColor(activeBgColor);
            g2.fill(forma);

        } else {
            // --- ESTADO INACTIVO (Transparente / Fondo Rojo) ---
            setForeground(inactiveTextColor);

            // Resplandor translúcido al pasar el cursor (Hover)
            if (progresoHover > 0.01f) {
                g2.setColor(new Color(255, 255, 255, (int) (progresoHover * 35)));
                g2.fill(forma);
            }
        }

        g2.dispose();

        // Renderizar el texto e ícono por encima
        super.paintComponent(g);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        repaint();
    }

    // --- GETTERS Y SETTERS ---

    public Color getActiveBgColor() {
        return activeBgColor;
    }

    public void setActiveBgColor(Color activeBgColor) {
        this.activeBgColor = activeBgColor;
        repaint();
    }

    public Color getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(Color activeTextColor) {
        this.activeTextColor = activeTextColor;
        repaint();
    }

    public Color getInactiveTextColor() {
        return inactiveTextColor;
    }

    public void setInactiveTextColor(Color inactiveTextColor) {
        this.inactiveTextColor = inactiveTextColor;
        repaint();
    }
}