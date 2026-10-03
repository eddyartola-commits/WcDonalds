package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

/**
 * Botón con fondo transparente, borde rojo redondeado e icono de basurero
 * diseñado estilo McDonald's para la acción "LIMPIAR ORDEN".
 */
public class LimpiarOrden extends JButton implements Serializable {

    // Paleta de colores oficiales
    private Color colorRojo = new Color(218, 41, 28);           // Rojo McDonald's (#DA291C)
    private Color colorRojoHover = new Color(255, 235, 235);    // Fondo sutil al pasar el cursor
    private Color colorRojoClick = new Color(255, 210, 210);    // Fondo al hacer clic
    private Color colorFondoActual = new Color(0, 0, 0, 0);     // Transparente por defecto

    private float escala = 1.0f;
    private float escalaObjetivo = 1.0f;
    private Timer animacion;

    public LimpiarOrden() {
        this("LIMPIAR ORDEN");
    }

    public LimpiarOrden(String text) {
        super(text);

        // Limpieza de estilos Swing nativos
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        // Estilos de texto y cursor
        setFont(new Font("Arial Black", Font.BOLD, 15));
        setForeground(colorRojo);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(350, 55));
        setHorizontalAlignment(SwingConstants.CENTER);
        setIconTextGap(10);

        // Icono predeterminado de basurero si no se asigna imagen
        setText("🗑  " + text);

        // Animación suave de escalado (60 FPS)
        animacion = new Timer(15, e -> {
            if (Math.abs(escala - escalaObjetivo) > 0.001f) {
                escala += (escalaObjetivo - escala) * 0.20f;
                repaint();
            } else {
                escala = escalaObjetivo;
                animacion.stop();
            }
        });

        // Eventos del Mouse para efectos visuales (Hover / Click)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                colorFondoActual = colorRojoHover;
                escalaObjetivo = 1.02f;
                if (!animacion.isRunning()) animacion.start();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                colorFondoActual = new Color(0, 0, 0, 0); // Vuelve a transparente
                escalaObjetivo = 1.0f;
                if (!animacion.isRunning()) animacion.start();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                colorFondoActual = colorRojoClick;
                escalaObjetivo = 0.97f;
                if (!animacion.isRunning()) animacion.start();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                colorFondoActual = contains(e.getPoint()) ? colorRojoHover : new Color(0, 0, 0, 0);
                escalaObjetivo = contains(e.getPoint()) ? 1.02f : 1.0f;
                if (!animacion.isRunning()) animacion.start();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Activación de Anti-aliasing para bordes curvos y tipografía nítida
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Aplicar la escala animada desde el centro
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        g2.scale(escala, escala);
        g2.translate((centerX / escala) - centerX, (centerY / escala) - centerY);

        int marginX = 2;
        int marginY = 2;
        int radioCurva = 22; // Nivel de redondeado exactamente como en la foto

        Shape forma = new RoundRectangle2D.Float(
            marginX, 
            marginY, 
            width - (marginX * 2) - 1, 
            height - (marginY * 2) - 1, 
            radioCurva, 
            radioCurva
        );

        // 1. Dibujar el fondo transparente o el tinte al hacer Hover
        if (colorFondoActual.getAlpha() > 0) {
            g2.setColor(colorFondoActual);
            g2.fill(forma);
        }

        // 2. Dibujar el Borde Rojo Redondeado
        g2.setColor(colorRojo);
        g2.setStroke(new BasicStroke(2.2f)); // Grosor idéntico a la imagen
        g2.draw(forma);

        g2.dispose();

        // Renderizado del texto/icono
        super.paintComponent(g);
    }
}