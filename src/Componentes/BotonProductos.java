package Componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.Timer;

public class BotonProductos extends JButton {

    private float hover = 0f;
    private final Timer animacion;

    public BotonProductos() {
        this("Productos");
    }

    public BotonProductos(String texto) {
        super(texto);

        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setForeground(new Color(30, 30, 30));
        setPreferredSize(new Dimension(190, 40));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);

        animacion = new Timer(15, e -> {
            float objetivo =
                    isEnabled() && getModel().isRollover() ? 1f : 0f;

            hover += (objetivo - hover) * 0.2f;

            if (Math.abs(objetivo - hover) < 0.01f) {
                hover = objetivo;
                ((Timer) e.getSource()).stop();
            }

            repaint();
        });

        getModel().addChangeListener(e -> {
            if (!animacion.isRunning()) {
                animacion.start();
            }

            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D dibujo = (Graphics2D) g.create();

        dibujo.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color fondo = new Color(
                255,
                Math.round(255 - 20 * hover),
                Math.round(255 - 55 * hover)
        );

        if (isEnabled()
                && getModel().isPressed()
                && getModel().isArmed()) {
            fondo = new Color(255, 195, 0);
        }

        dibujo.setColor(fondo);
        dibujo.fillRoundRect(
                2, 2,
                Math.max(0, getWidth() - 4),
                Math.max(0, getHeight() - 4),
                18, 18
        );

        dibujo.dispose();
        super.paintComponent(g);
    }

    @Override
    public void removeNotify() {
        if (animacion != null) {
            animacion.stop();
        }

        super.removeNotify();
    }
}