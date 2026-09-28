    package Componentes;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.awt.geom.RoundRectangle2D;
    import java.io.Serializable;

    public class BotonCategoria extends JButton implements Serializable {

        // Paleta Oficial de McDonald's
        private Color activeBgColor = Color.decode("#FFBC0D");      // Golden Arches Yellow
        private Color activeTextColor = Color.decode("#27251F");    // Black/Dark Charcoal
        private Color inactiveBgColor = Color.WHITE;               // Pure White
        private Color inactiveTextColor = Color.decode("#27251F");  // Neutral Dark
        private Color borderColor = Color.decode("#E5E5E5");        // Soft Grey Border

        // Variables de Animación
        private float escala = 1.0f;
        private float escalaObjetivo = 1.0f;
        private float progresoHover = 0.0f;
        private float progresoHoverObjetivo = 0.0f;
        private Timer animacion;

        // Constructor sin parámetros indispensable para la paleta de NetBeans
        public BotonCategoria() {
            this("Hamburguesas");
        }

        public BotonCategoria(String text) {
            super(text);

            // Limpieza de estilos nativos de Swing
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);

            // Estilo de texto y alineación
            setFont(new Font("Segoe UI", Font.BOLD, 15));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(170, 50));
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(12);

            // Timer de animación suave (aprox 60 FPS)
            animacion = new Timer(15, e -> {
                boolean necesitaRepaint = false;

                // Interpolación de Escala (Zoom)
                if (Math.abs(escala - escalaObjetivo) > 0.001f) {
                    escala += (escalaObjetivo - escala) * 0.20f;
                    necesitaRepaint = true;
                } else {
                    escala = escalaObjetivo;
                }

                // Interpolación de Hover
                if (Math.abs(progresoHover - progresoHoverObjetivo) > 0.01f) {
                    progresoHover += (progresoHoverObjetivo - progresoHover) * 0.18f;
                    necesitaRepaint = true;
                } else {
                    progresoHover = progresoHoverObjetivo;
                }

                if (necesitaRepaint) {
                    repaint();
                } else {
                    animacion.stop();
                }
            });

            // Evento para alternar selección única entre botones hermanos
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getParent() != null) {
                        for (Component c : getParent().getComponents()) {
                            if (c instanceof BotonCategoria) {
                                ((BotonCategoria) c).setSelected(false);
                            }
                        }
                    }
                    setSelected(true);
                }
            });

            // Eventos del mouse para la animación
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    escalaObjetivo = 1.03f;
                    progresoHoverObjetivo = 1.0f;
                    if (!animacion.isRunning()) animacion.start();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    escalaObjetivo = 1.0f;
                    progresoHoverObjetivo = 0.0f;
                    if (!animacion.isRunning()) animacion.start();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    escalaObjetivo = 0.96f;
                    if (!animacion.isRunning()) animacion.start();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    escalaObjetivo = contains(e.getPoint()) ? 1.03f : 1.0f;
                    if (!animacion.isRunning()) animacion.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Anti-aliasing para bordes curvos y tipografía
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Aplicar la escala animada desde el centro
            double centerX = width / 2.0;
            double centerY = height / 2.0;
            g2.scale(escala, escala);
            g2.translate((centerX / escala) - centerX, (centerY / escala) - centerY);

            int marginX = 4;
            int marginY = 3;
            int arc = height - (marginY * 2);

            Shape forma = new RoundRectangle2D.Float(marginX, marginY, width - (marginX * 2), height - (marginY * 2), arc, arc);

            if (isSelected()) {
                setForeground(activeTextColor);

                // Sombra dorada cálida en estado activo
                int alphaSombra = (int) (40 + (progresoHover * 30));
                g2.setColor(new Color(255, 188, 13, alphaSombra));
                g2.fillRoundRect(marginX, marginY + 3, width - (marginX * 2), height - (marginY * 2), arc, arc);

                // Fondo amarillo McDonald's
                g2.setColor(activeBgColor);
                g2.fill(forma);

            } else {
                setForeground(inactiveTextColor);

                // Sombra ambiental suave
                int alphaSombra = (int) (10 + (progresoHover * 20));
                g2.setColor(new Color(0, 0, 0, alphaSombra));
                g2.fillRoundRect(marginX, marginY + 2, width - (marginX * 2), height - (marginY * 2), arc, arc);

                // Fondo inactivo (blanco con ligera elevación en hover)
                Color bgInactivo = mezclarColores(inactiveBgColor, new Color(245, 245, 245), progresoHover);
                g2.setColor(bgInactivo);
                g2.fill(forma);

                // Borde gris sutil
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(forma);
            }

            g2.dispose();

            // Renderizado del texto e icono dentro de la vista transformada
            super.paintComponent(g);
        }

        @Override
        public void setSelected(boolean b) {
            super.setSelected(b);
            repaint();
        }

        private Color mezclarColores(Color c1, Color c2, float ratio) {
            int r = (int) (c1.getRed() + ratio * (c2.getRed() - c1.getRed()));
            int g = (int) (c1.getGreen() + ratio * (c2.getGreen() - c1.getGreen()));
            int b = (int) (c1.getBlue() + ratio * (c2.getBlue() - c1.getBlue()));
            return new Color(r, g, b);
        }

        // --- GETTERS Y SETTERS ---

        public Color getActiveBgColor() {
            return activeBgColor;
        }

        public void setActiveBgColor(Color activeBgColor) {
            this.activeBgColor = activeBgColor;
            repaint();
        }

        public Color getActiveTextColor() {
            return activeTextColor;
        }

        public void setActiveTextColor(Color activeTextColor) {
            this.activeTextColor = activeTextColor;
            repaint();
        }

        public Color getInactiveBgColor() {
            return inactiveBgColor;
        }

        public void setInactiveBgColor(Color inactiveBgColor) {
            this.inactiveBgColor = inactiveBgColor;
            repaint();
        }
    }