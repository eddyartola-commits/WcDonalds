package Componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import javax.swing.JButton;
import javax.swing.Timer;

public class BotonVerde extends JButton {

    private float factorDerretido = 0f; // 0.0 (normal) a 1.0 (totalmente derretido)
    private Timer timer;
    private final int ESPACIO_GOTAS = 20; // Espacio reservado abajo para las gotas

    public BotonVerde() {
        setText("Ingresar al sistema");
        setFont(new Font("Arial", Font.BOLD, 21));
        setForeground(Color.WHITE);
        setBackground(new Color(0x27742D)); // Verde #27742D
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animarDerretido(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                animarDerretido(false);
            }
        });
    }

    private void animarDerretido(boolean derretir) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(10, e -> {
            float paso = 0.12f;
            if (derretir && factorDerretido < 1.0f) {
                factorDerretido += paso;
                if (factorDerretido > 1.0f) factorDerretido = 1.0f;
                repaint();
            } else if (!derretir && factorDerretido > 0.0f) {
                factorDerretido -= paso;
                if (factorDerretido < 0.0f) factorDerretido = 0.0f;
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
        int baseHeight = height - ESPACIO_GOTAS;

        // Color según estado de interacción
        Color colorActual;
        if (getModel().isPressed()) {
            colorActual = getBackground().darker();
        } else if (getModel().isRollover()) {
            colorActual = getBackground().brighter();
        } else {
            colorActual = getBackground();
        }
        g2.setColor(colorActual);

        float f = factorDerretido;

        Path2D.Float path = new Path2D.Float();
        float arc = 18f;

        // Borde superior y laterales redondeados
        path.moveTo(arc, 0);
        path.lineTo(width - arc, 0);
        path.quadTo(width, 0, width, arc);
        path.lineTo(width, baseHeight - arc);
        path.quadTo(width, baseHeight, width - arc, baseHeight);

        // --- TRAZADO DE GOTAS SLIME ---
        
        // Gota 1: Pequeña (Derecha)
        path.curveTo(
            width * 0.90f, baseHeight + (4f * f),
            width * 0.88f, baseHeight + (10f * f),
            width * 0.85f, baseHeight + (10f * f)
        );
        path.curveTo(
            width * 0.82f, baseHeight + (10f * f),
            width * 0.81f, baseHeight + (2f * f),
            width * 0.77f, baseHeight + (2f * f)
        );

        // Gota 2: Mediana (Centro-Derecha)
        path.curveTo(
            width * 0.73f, baseHeight + (3f * f),
            width * 0.72f, baseHeight + (16f * f),
            width * 0.67f, baseHeight + (16f * f)
        );
        path.curveTo(
            width * 0.62f, baseHeight + (16f * f),
            width * 0.62f, baseHeight + (2f * f),
            width * 0.56f, baseHeight + (2f * f)
        );

        // Gota 3: Gota Larga con Bulbo / Lágrima (Centro-Izquierda)
        path.curveTo(
            width * 0.52f, baseHeight + (4f * f),
            width * 0.51f, baseHeight + (22f * f),
            width * 0.46f, baseHeight + (22f * f)
        );
        path.curveTo(
            width * 0.41f, baseHeight + (22f * f),
            width * 0.41f, baseHeight + (4f * f),
            width * 0.35f, baseHeight + (3f * f)
        );

        // Gota 4: Mediana-Izquierda
        path.curveTo(
            width * 0.30f, baseHeight + (3f * f),
            width * 0.28f, baseHeight + (12f * f),
            width * 0.23f, baseHeight + (12f * f)
        );
        path.curveTo(
            width * 0.18f, baseHeight + (12f * f),
            width * 0.17f, baseHeight + (1f * f),
            arc, baseHeight
        );

        // Esquinas inferiores izquierdas
        path.quadTo(0, baseHeight, 0, baseHeight - arc);
        path.lineTo(0, arc);
        path.quadTo(0, 0, arc, 0);
        path.closePath();

        // Pinta el cuerpo con las gotas estilo derretido
        g2.fill(path);
        g2.dispose();

        // --- DIBUJAR TEXTO CENTRADO ---
        // Compensamos verticalmente el texto trasladándolo hacia arriba la mitad del espacio de las gotas
        Graphics gTexto = g.create();
        gTexto.translate(0, -ESPACIO_GOTAS / 2);
        super.paintComponent(gTexto);
        gTexto.dispose();
    }
}