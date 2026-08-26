/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author EMILIANI SOMASCOS
 */
public class boton extends JButton {

    private boolean hover = false;

    public boton() {
        setText("INICIAR SESIÓN");
        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(380, 50));

        // Cambia de tono sutilmente al pasar el mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arc = 20; // Redondeo de esquinas

        // Degradado rojo exacto
        Color colorArriba = hover ? new Color(215, 30, 30) : new Color(195, 25, 25);
        Color colorAbajo = hover ? new Color(170, 15, 15) : new Color(145, 10, 10);

        GradientPaint degradado = new GradientPaint(0, 0, colorArriba, 0, height, colorAbajo);
        g2.setPaint(degradado);

        Shape forma = new RoundRectangle2D.Float(0, 0, width, height, arc, arc);
        g2.fill(forma);

        g2.dispose();
        super.paintComponent(g);
    }
}