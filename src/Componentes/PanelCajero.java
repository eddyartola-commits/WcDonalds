package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class PanelCajero extends JPanel implements Serializable {

    // Configuración de Color y Bordes
    private Color backgroundColor = Color.decode("#FCF1DF"); // Color Beige de la imagen
    private Color shadowColor = new Color(0, 0, 0, 30);      // Sombra suave transparente
    private int cornerRadius = 35;                            // Radio de redondeo
    private int shadowSize = 8;                               // Grosor/difuminado de la sombra

    public PanelCajero() {
        setOpaque(false);
        setLayout(new BorderLayout());
        // Espaciado interno para evitar que los elementos colisionen con los bordes curvos
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing para suavizado de bordes gráficos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 1. DIBUJAR SOMBRA DIFUMINADA (Varias capas con transparencia decreciente)
        for (int i = 0; i < shadowSize; i++) {
            int alpha = (int) (shadowColor.getAlpha() * (1.0f - (float) i / shadowSize));
            g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), alpha));
            
            // Se desplaza ligeramente hacia abajo para simular luz superior
            g2.fillRoundRect(
                    i, 
                    i + 2, 
                    width - (i * 2), 
                    height - (i * 2), 
                    cornerRadius, 
                    cornerRadius
            );
        }

        // 2. DIBUJAR EL PANEL PRINCIPAL (#FCF1DF)
        int x = shadowSize;
        int y = shadowSize;
        int cardWidth = width - (shadowSize * 2);
        int cardHeight = height - (shadowSize * 2);

        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Float(x, y, cardWidth, cardHeight, cornerRadius, cornerRadius));

        // 3. OPCIONAL: Borde sutil blanco interno para resaltar
        g2.setColor(new Color(255, 255, 255, 120));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(x, y, cardWidth, cardHeight, cornerRadius, cornerRadius));

        g2.dispose();
        super.paintComponent(g);
    }

    // --- GETTERS Y SETTERS PARA PERSONALIZAR DESDE PROPERTIES EN NETBEANS ---

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
        repaint();
    }
}