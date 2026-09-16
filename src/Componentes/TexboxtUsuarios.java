package Componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class TexboxtUsuarios extends JTextField implements Serializable {

    private String placeholder = "Ingrese el nombre";
    private Color placeholderColor = new Color(160, 160, 160);
    private Color borderColor = new Color(210, 215, 222);
    private Color focusBorderColor = new Color(255, 188, 13); // Amarillo McDonald's al enfocar
    private int arc = 20;

    public TexboxtUsuarios() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(new Color(50, 50, 50));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setCaretColor(new Color(50, 50, 50));
        
        // Espaciado interno para que el texto no toque las esquinas curvas
        setBorder(new EmptyBorder(8, 15, 8, 15));
        setPreferredSize(new Dimension(280, 42));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Suavizado gráfico de bordes y texto
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. Dibujar fondo blanco redondeado
        Shape forma = new RoundRectangle2D.Float(1, 1, w - 3, h - 3, arc, arc);
        g2.setColor(getBackground());
        g2.fill(forma);

        // 2. Dibujar borde redondeado (cambia al ganar foco)
        g2.setColor(hasFocus() ? focusBorderColor : borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(forma);

        // 3. Dibujar Placeholder si el campo está vacío y no tiene texto activo
        if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty()) {
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            
            FontMetrics fm = g2.getFontMetrics();
            Insets insets = getInsets();
            int y = (h - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.drawString(placeholder, insets.left, y);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    // --- GETTERS Y SETTERS PARA LA PALETA DE NETBEANS ---

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public Color getPlaceholderColor() {
        return placeholderColor;
    }

    public void setPlaceholderColor(Color placeholderColor) {
        this.placeholderColor = placeholderColor;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }
}