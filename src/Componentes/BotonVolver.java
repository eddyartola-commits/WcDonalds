package Componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

public class BotonVolver extends JButton {

    private Image imagen;
    private float escala = 1.0f;
    private float escalaObjetivo = 1.0f;
    private Timer animacion;

    public BotonVolver() {

        URL ruta = getClass().getResource("/Imagenes/volver.png");

        if (ruta != null) {
            imagen = new ImageIcon(ruta).getImage();
        } else {
            System.out.println("No se encontró /Imagenes/volver.png");
        }

        setText("");
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        animacion = new Timer(15, e -> {

            if (Math.abs(escala - escalaObjetivo) < 0.005f) {
                escala = escalaObjetivo;
                animacion.stop();
            } else {
                escala += (escalaObjetivo - escala) * 0.18f;
            }

            repaint();
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                escalaObjetivo = 1.05f;
                animacion.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                escalaObjetivo = 1.0f;
                animacion.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                escalaObjetivo = 0.95f;
                animacion.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                escalaObjetivo = contains(e.getPoint()) ? 1.05f : 1.0f;
                animacion.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        // Suavizado de bordes e imágenes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        int width = getWidth();
        int height = getHeight();

        // Aplicar la escala desde el centro exacto del componente
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        g2.scale(escala, escala);
        g2.translate((centerX / escala) - centerX, (centerY / escala) - centerY);

        int margin = 6;
        int arc = 20;

        // 1. Sombra suave animada
        g2.setColor(new Color(0, 0, 0, 15));
        g2.fillRoundRect(margin + 1, margin + 2, width - (margin * 2), height - (margin * 2), arc, arc);

        // 2. Fondo blanco animado
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(margin, margin, width - (margin * 2) - 2, height - (margin * 2) - 2, arc, arc);

        // 3. Borde animado (cambia a un tono ligeramente más oscuro en hover para dar feedback visual)
        Color colorBorde = (escala > 1.01f) ? new Color(200, 200, 200) : new Color(230, 230, 230);
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(margin, margin, width - (margin * 2) - 2, height - (margin * 2) - 2, arc, arc);

        // 4. Dibujar imagen de retorno
        if (imagen != null) {
            int anchoDisponible = width - (margin * 4);
            int altoDisponible = height - (margin * 4);

            int anchoImagen = imagen.getWidth(this);
            int altoImagen = imagen.getHeight(this);

            if (anchoImagen > 0 && altoImagen > 0) {
                double proporcion = Math.min(
                        (double) anchoDisponible / anchoImagen,
                        (double) altoDisponible / altoImagen
                );

                int nuevoAncho = (int) (anchoImagen * proporcion);
                int nuevoAlto = (int) (altoImagen * proporcion);

                int x = (width - nuevoAncho) / 2;
                int y = (height - nuevoAlto) / 2;

                g2.drawImage(imagen, x, y, nuevoAncho, nuevoAlto, this);
            }
        }

        g2.dispose();
    }
}