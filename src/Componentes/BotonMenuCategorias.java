package Componentes;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.Timer;

public class BotonMenuCategorias extends JButton {

    private float hover = 0f;
    private final Timer animacion;

    public BotonMenuCategorias() {
        super("Categorías");

        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setForeground(Color.WHITE);
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
                Math.round(200 - 25 * hover),
                0,
                Math.round(25 - 5 * hover)
        );

        if (isEnabled()
                && getModel().isPressed()
                && getModel().isArmed()) {
            fondo = new Color(150, 0, 15);
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