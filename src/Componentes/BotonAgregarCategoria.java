package Componentes;

import java.awt.*;
import javax.swing.JButton;

public class BotonAgregarCategoria extends JButton {

    public BotonAgregarCategoria() {
        super("+ Agregar categoría");

        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(190, 42));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);

        getModel().addChangeListener(e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D dibujo = (Graphics2D) g.create();

        dibujo.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color fondo = new Color(200, 0, 25);

        if (isEnabled() && getModel().isRollover()) {
            fondo = new Color(180, 0, 20);
        }

        if (isEnabled()
                && getModel().isPressed()
                && getModel().isArmed()) {
            fondo = new Color(145, 0, 15);
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
}