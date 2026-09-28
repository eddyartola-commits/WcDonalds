package Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelRedondeadoSombra extends JPanel {

    private int arcoRedondeo = 25; // Radio de las esquinas
    private int tamanoSombra = 7;  // Grosor de la sombra
    private Color colorSombra = new Color(0, 0, 0, 20); // Negro transparente (opacidad baja)

    public PanelRedondeadoSombra() {
        setOpaque(false); // Indispensable para que se vean la sombra y curvas
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Activar suavizado de bordes (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // 1. Dibujar difuminado de la sombra
        for (int i = 0; i < tamanoSombra; i++) {
            g2.setColor(new Color(0, 0, 0, 5)); // Gradiente difuminado
            g2.fillRoundRect(
                i, 
                i, 
                ancho - (i * 2), 
                alto - (i * 2), 
                arcoRedondeo, 
                arcoRedondeo
            );
        }

        // 2. Dibujar el fondo blanco principal
        g2.setColor(getBackground());
        g2.fillRoundRect(
            tamanoSombra, 
            tamanoSombra, 
            ancho - (tamanoSombra * 2), 
            alto - (tamanoSombra * 2), 
            arcoRedondeo, 
            arcoRedondeo
        );

        g2.dispose();
    }
}