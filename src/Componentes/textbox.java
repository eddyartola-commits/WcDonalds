package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class textbox extends JPanel {

    private JTextField txtInput;
    private JLabel lblIcono;
    private String placeholder = "Ingrese texto";
    private int anchoIcono = 90; // Ancho ampliado a la derecha (horizontal)

    public textbox() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(400, 52));

        // Panel para el icono con mayor espacio horizontal (90px)
        JPanel panelIcono = new JPanel(new GridBagLayout());
        panelIcono.setPreferredSize(new Dimension(anchoIcono, 52));
        panelIcono.setOpaque(false);

        lblIcono = new JLabel();
        panelIcono.add(lblIcono);

        // Campo de texto con placeholder
        txtInput = new JTextField(placeholder);
        txtInput.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtInput.setForeground(new Color(160, 160, 160));
        txtInput.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
        txtInput.setOpaque(false);

        // Limpiar/restaurar placeholder al enfocar
        txtInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtInput.getText().equals(placeholder)) {
                    txtInput.setText("");
                    txtInput.setForeground(new Color(30, 30, 30));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtInput.getText().trim().isEmpty()) {
                    txtInput.setText(placeholder);
                    txtInput.setForeground(new Color(160, 160, 160));
                }
            }
        });

        add(panelIcono, BorderLayout.WEST);
        add(txtInput, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arc = 20;

        // 1. Fondo blanco redondeado
        Shape formaCompleta = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, arc, arc);
        g2.setColor(Color.WHITE);
        g2.fill(formaCompleta);

        // 2. Fondo rosado extendido horizontalmente hasta el nuevo ancho (90px)
        g2.setClip(formaCompleta);
        g2.setColor(new Color(250, 232, 232));
        g2.fillRect(0, 0, anchoIcono, height);
        g2.setClip(null);

        // 3. Borde rojo exterior
        g2.setColor(new Color(225, 110, 110));
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(formaCompleta);

        g2.dispose();
        super.paintComponent(g);
    }

    public String getTexto() {
        String texto = txtInput.getText();
        return texto.equals(placeholder) ? "" : texto;
    }

    public void setImagenIcono(ImageIcon icon) {
        lblIcono.setIcon(icon);
    }

    public void setPlaceholder(String texto) {
        this.placeholder = texto;
        txtInput.setText(texto);
    }
}