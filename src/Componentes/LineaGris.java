/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author EMILIANI SOMASCOS
 */
public class LineaGris extends JComponent {

    public LineaGris() {
        setPreferredSize(new Dimension(380, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing para un trazo limpio
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Color gris ultra suave idéntico a la imagen
        g2.setColor(new Color(230, 230, 230));
        g2.setStroke(new BasicStroke(1.0f));

        // Dibujar la línea horizontal a lo largo del componente
        int y = getHeight() / 2;
        g2.drawLine(0, y, getWidth(), y);

        g2.dispose();
    }
}