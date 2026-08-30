package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class BotonAdmi extends JButton implements Serializable {

    private Color lineColor = new Color(180, 0, 0);
    private Color activeTextColor = new Color(180, 0, 0);
    private Color inactiveTextColor = new Color(40, 40, 40);

    // Constructor sin parámetros para NetBeans
    public BotonAdmi() {
        this("Opción");
    }

    public BotonAdmi(String text) {
        super(text);

        // Estilo base
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        // Fuente actualizada a Arial Black
        setFont(new Font("Arial Black", Font.BOLD, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Evento que conmuta el estado al hacer clic
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getParent() != null) {
                    // Desmarca todos los demás BotonAdmi en el mismo panel
                    for (Component c : getParent().getComponents()) {
                        if (c instanceof BotonAdmi) {
                            ((BotonAdmi) c).setSelected(false);
                        }
                    }
                }
                // Activa únicamente el botón presionado
                setSelected(true);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Suavizado de bordes (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Fondo blanco
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. Control visual de estado (Seleccionado vs Inactivo)
        if (isSelected()) {
            setForeground(activeTextColor);

            // Dibujar la barra roja inferior solo si está activo
            int lineHeight = 4;
            int lineMargin = 8;
            int arc = 4;
            int x = lineMargin;
            int y = getHeight() - lineHeight - 2;
            int width = getWidth() - (lineMargin * 2);

            g2.setColor(lineColor);
            g2.fillRoundRect(x, y, width, lineHeight, arc, arc);
        } else {
            setForeground(inactiveTextColor);
        }

        g2.dispose();

        // 3. Dibujar el texto del botón
        super.paintComponent(g);
    }

    // Sobrescribimos setSelected para forzar la actualización visual al cambiar el estado
    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        repaint();
    }

    // Getters y Setters para las propiedades de NetBeans
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    public Color getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(Color activeTextColor) {
        this.activeTextColor = activeTextColor;
        repaint();
    }

    public Color getInactiveTextColor() {
        return inactiveTextColor;
    }

    public void setInactiveTextColor(Color inactiveTextColor) {
        this.inactiveTextColor = inactiveTextColor;
        repaint();
    }
}