package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author EMILIANI SOMASCOS
 */
public class boton extends JButton {

    // Variables de animación
    private float progresoHover = 0.0f;
    private float progresoHoverObjetivo = 0.0f;

    private float escala = 1.0f;
    private float escalaObjetivo = 1.0f;

    private Timer animacion;

    // Colores base y hover (#e3202d y un tono más vivo)
    private Color colorBase = Color.decode("#e3202d");
    private Color colorHover = Color.decode("#ff2e3b");

    public boton() {
        setText("INICIAR SESIÓN");
        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(380, 50));

        // Timer de animación fluido (60 FPS)
        animacion = new Timer(15, e -> {
            boolean necesitaRepaint = false;

            // Transición de color al pasar el mouse
            if (Math.abs(progresoHover - progresoHoverObjetivo) > 0.01f) {
                progresoHover += (progresoHoverObjetivo - progresoHover) * 0.18f;
                necesitaRepaint = true;
            } else {
                progresoHover = progresoHoverObjetivo;
            }

            // Transición de escala al pasar el mouse o dar clic
            if (Math.abs(escala - escalaObjetivo) > 0.001f) {
                escala += (escalaObjetivo - escala) * 0.20f;
                necesitaRepaint = true;
            } else {
                escala = escalaObjetivo;
            }

            if (necesitaRepaint) {
                repaint();
            } else {
                animacion.stop();
            }
        });

        // Escuchadores del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                activarHover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                desactivarHover();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                escalaObjetivo = 0.95f; // Se comprime un poco al presionar
                if (!animacion.isRunning()) animacion.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                escalaObjetivo = contains(e.getPoint()) ? 1.04f : 1.0f;
                if (!animacion.isRunning()) animacion.start();
            }
        });

        // Asegura la animación al mover el mouse internamente
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (progresoHoverObjetivo != 1.0f) {
                    activarHover();
                }
            }
        });
    }

    private void activarHover() {
        progresoHoverObjetivo = 1.0f;
        escalaObjetivo = 1.04f; // Crece un 4% al pasar el mouse por encima
        if (!animacion.isRunning()) animacion.start();
    }

    private void desactivarHover() {
        progresoHoverObjetivo = 0.0f;
        escalaObjetivo = 1.0f; // Vuelve a su tamaño normal
        if (!animacion.isRunning()) animacion.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Anti-aliasing de alta calidad
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arc = 20;

        // Margen defensivo para dar espacio al escalado sin recortar esquinas
        int marginX = 6;
        int marginY = 4;

        // Escalado animado desde el centro exacto
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        g2.scale(escala, escala);
        g2.translate((centerX / escala) - centerX, (centerY / escala) - centerY);

        // 1. Sombra suave expandible
        int alphaSombra = (int) (15 + (progresoHover * 35));
        g2.setColor(new Color(227, 32, 45, alphaSombra));
        g2.fillRoundRect(marginX, marginY + 2, width - (marginX * 2), height - (marginY * 2), arc, arc);

        // 2. Transición suave de color
        Color colorActual = mezclarColores(colorBase, colorHover, progresoHover);
        g2.setColor(colorActual);

        // 3. Relleno del botón redondeado
        Shape forma = new RoundRectangle2D.Float(marginX, marginY, width - (marginX * 2), height - (marginY * 2), arc, arc);
        g2.fill(forma);

        // 4. Borde brillante blanco en hover
        if (progresoHover > 0.01f) {
            g2.setColor(new Color(255, 255, 255, (int) (progresoHover * 80)));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(forma);
        }

        g2.dispose();

        // Renderiza el texto centrado
        super.paintComponent(g);
    }

    private Color mezclarColores(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() + ratio * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + ratio * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + ratio * (c2.getBlue() - c1.getBlue()));
        return new Color(r, g, b);
    }
}