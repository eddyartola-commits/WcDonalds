package Componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.Serializable;

public class Buscador extends JTextField implements Serializable {

    private String placeholder = "Buscar productos...";
    private Color placeholderColor = new Color(140, 140, 140);
    private Color textColor = new Color(40, 40, 40);
    private Color borderColor = new Color(225, 225, 225);
    private int arcRadius = 40;

    public Buscador() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(textColor);
        setFont(new Font("Segoe UI", Font.PLAIN, 15));
        setCaretColor(textColor);

        // Iniciar con margen a la izquierda para el placeholder y la lupa
        actualizarMargenInterno();
        setPreferredSize(new Dimension(350, 45));

        // Listener para redibujar al ganar/perder el foco
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                actualizarMargenInterno();
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                actualizarMargenInterno();
                repaint();
            }
        });

        // Repintar inmediatamente cuando se inserta o borra texto
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
    }

    private void actualizarMargenInterno() {
        // Mantiene espacio para la lupa a la izquierda
        setBorder(new EmptyBorder(8, 42, 8, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int margin = 4;

        int arc = Math.min(arcRadius, height - (margin * 2));

        // 1. Sombra exterior
        for (int i = 1; i <= 3; i++) {
            g2.setColor(new Color(0, 0, 0, 4 * (4 - i)));
            g2.fillRoundRect(margin - i, margin + (i / 2), width - ((margin - i) * 2), height - (margin * 2), arc, arc);
        }

        // 2. Fondo blanco
        g2.setColor(getBackground());
        g2.fillRoundRect(margin, margin, width - (margin * 2), height - (margin * 2), arc, arc);

        // 3. Borde suave
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(margin, margin, width - (margin * 2) - 1, height - (margin * 2) - 1, arc, arc);

        // 4. Dibujar Lupa Vectorial
        dibujarLupa(g2, margin + 14, height / 2);

        g2.dispose();

        // 5. Dibujar el placeholder visual SOLO si el campo está vacío Y NO tiene el foco
        if (getText().isEmpty() && !hasFocus()) {
            Graphics2D gText = (Graphics2D) g.create();
            gText.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            gText.setColor(placeholderColor);
            gText.setFont(getFont());

            FontMetrics fm = gText.getFontMetrics();
            int yText = (height - fm.getHeight()) / 2 + fm.getAscent();

            gText.drawString(placeholder, 42, yText);
            gText.dispose();
        }

        // 6. Renderizar el texto que escribes
        super.paintComponent(g);
    }

    private void dibujarLupa(Graphics2D g2, int x, int centerY) {
        g2.setColor(new Color(30, 30, 30));
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int radio = 5;
        int lupaX = x + radio;
        int lupaY = centerY - 1;

        g2.drawOval(lupaX - radio, lupaY - radio, radio * 2, radio * 2);

        int x1 = (int) (lupaX + radio * Math.cos(Math.toRadians(45)));
        int y1 = (int) (lupaY + radio * Math.sin(Math.toRadians(45)));
        g2.drawLine(x1, y1, x1 + 5, y1 + 5);
    }

    // --- GETTERS Y SETTERS ---

    public int getArcRadius() {
        return arcRadius;
    }

    public void setArcRadius(int arcRadius) {
        this.arcRadius = arcRadius;
        repaint();
    }

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
}