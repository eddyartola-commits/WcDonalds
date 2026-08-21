
package Componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.Timer;

public class BotonAzul extends JButton {

    private int offsetY = 0; // Desplazamiento vertical para el efecto
    private final int MAX_OFFSET = 8; // Se incrementa a 8px para una elevación mucho más visible
    private Timer timer;

    public BotonAzul() {
        setText("Ingresar al sistema");
        setFont(new Font("Arial", Font.BOLD, 21));
        setForeground(Color.WHITE);
        setBackground(new Color(0, 71, 171));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Listener para detectar la entrada y salida del ratón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animarBoton(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animarBoton(false);
            }
        });
    }

    private void animarBoton(boolean subir) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Animación rápida y fluida
        timer = new Timer(8, e -> {
            if (subir && offsetY < MAX_OFFSET) {
                offsetY++;
                repaint();
            } else if (!subir && offsetY > 0) {
                offsetY--;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 1. Pinta la sombra amplia si el botón se eleva
        if (offsetY > 0) {
            g2.setColor(new Color(0, 0, 0, Math.min(80, offsetY * 10)));
            g2.fillRoundRect(0, MAX_OFFSET, width, height - MAX_OFFSET, 25, 25);
        }

        // 2. Color según estado de interacción
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        // 3. Dibujar el cuerpo del botón elevado
        g2.fillRoundRect(0, MAX_OFFSET - offsetY, width, height - MAX_OFFSET, 25, 25);
        g2.dispose();

        // 4. Mueve el texto a la misma altura del cuerpo desplazado
        Graphics gTexto = g.create();
        gTexto.translate(0, -offsetY / 2);
        super.paintComponent(gTexto);
        gTexto.dispose();
    }
}