package Componentes;

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
                escalaObjetivo = 1.06f;
                animacion.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                escalaObjetivo = 1.0f;
                animacion.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                escalaObjetivo = 0.96f;
                animacion.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                escalaObjetivo = contains(e.getPoint()) ? 1.06f : 1.0f;
                animacion.start();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (imagen == null) {
            super.paintComponent(g);
            return;
        }  

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
        );

        int anchoDisponible = getWidth();
        int altoDisponible = getHeight();

        int anchoImagen = imagen.getWidth(this);
        int altoImagen = imagen.getHeight(this);

        if (anchoImagen <= 0 || altoImagen <= 0) {
            g2.dispose();
            return;
        }

        double proporcion = Math.min(
                (double) anchoDisponible / anchoImagen,
                (double) altoDisponible / altoImagen
        );

        int nuevoAncho = (int) (anchoImagen * proporcion * escala);
        int nuevoAlto = (int) (altoImagen * proporcion * escala);

        int x = (anchoDisponible - nuevoAncho) / 2;
        int y = (altoDisponible - nuevoAlto) / 2;

        g2.drawImage(
                imagen,
                x,
                y,
                nuevoAncho,
                nuevoAlto,
                this
        );

        g2.dispose();
    }
}