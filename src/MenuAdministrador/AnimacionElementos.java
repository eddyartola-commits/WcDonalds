package MenuAdministrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.util.*;

/** Animación de componentes reales sin modificar sus eventos ni el formulario. */
public final class AnimacionElementos {
    private final java.util.List<Efecto> efectos = new ArrayList<>();
    private javax.swing.Timer ciclo;
    private boolean saliendo;

    public void agregar(JComponent componente) {
        Container padre = componente.getParent();
        if (padre == null) return;
        LayoutManager layout = padre.getLayout();
        Object restricciones = null;
        if (layout instanceof GridBagLayout)
            restricciones = ((GridBagLayout) layout).getConstraints(componente);
        else if (layout instanceof BorderLayout)
            restricciones = ((BorderLayout) layout).getConstraints(componente);
        else throw new IllegalArgumentException("Layout no compatible: " + layout);
        int indice = padre.getComponentZOrder(componente);
        Efecto efecto = new Efecto();
        padre.remove(componente);
        JLayer<JComponent> capa = new JLayer<>(componente, efecto);
        efecto.capa = capa;
        padre.add(capa, restricciones, indice);
        efectos.add(efecto);
        if (componente instanceof AbstractButton) {
            AbstractButton boton = (AbstractButton) componente;
            boton.setRolloverEnabled(true);
            boton.getModel().addChangeListener(e -> {
                float destino = boton.getModel().isPressed() ? -1f
                        : (boton.getModel().isRollover() || boton.hasFocus() ? 1f : 0f);
                efecto.interactuar(destino);
            });
            boton.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override public void focusGained(java.awt.event.FocusEvent e) { efecto.interactuar(1); }
                @Override public void focusLost(java.awt.event.FocusEvent e) { efecto.interactuar(0); }
            });
        }
    }

    public void entrar() { ejecutar(false, null); }
    public void salir(Runnable fin) { ejecutar(true, fin); }
    public void detener() {
        if (ciclo != null) ciclo.stop();
        for (Efecto e : efectos) {
            if (e.interaccion != null) e.interaccion.stop();
            e.alpha = 1; e.desplazamiento = 0; e.capa.repaint();
        }
    }
    private void ejecutar(boolean salida, Runnable fin) {
        detener();
        saliendo = salida;
        final long inicio = System.nanoTime();
        final int duracion = salida ? 210 : 420;
        final int intervalo = salida ? 18 : 45;
        if (!salida) for (Efecto e : efectos) { e.alpha = 0; e.desplazamiento = 24; }
        ciclo = new javax.swing.Timer(16, evento -> {
            double tiempo = (System.nanoTime() - inicio) / 1000000.0;
            for (int i = 0; i < efectos.size(); i++) {
                Efecto e = efectos.get(i);
                int orden = salida ? efectos.size() - 1 - i : i;
                float t = (float) Math.max(0, Math.min(1, (tiempo - orden * intervalo) / duracion));
                float curva = salida ? t * t : 1 - (float) Math.pow(1-t, 3);
                e.alpha = salida ? 1-curva : curva;
                e.desplazamiento = salida ? -18 * curva : 24 * (1-curva);
                e.capa.repaint();
            }
            if (tiempo >= duracion + Math.max(0, efectos.size()-1)*intervalo) {
                ciclo.stop();
                if (fin != null) fin.run();
            }
        });
        ciclo.start();
    }
    private static final class Efecto extends LayerUI<JComponent> {
        JLayer<JComponent> capa;
        float alpha=1, desplazamiento, hover;
        javax.swing.Timer interaccion;
        void interactuar(float objetivo) {
            if (interaccion != null) interaccion.stop();
            interaccion = new javax.swing.Timer(16, e -> {
                hover += (objetivo-hover)*0.25f;
                if (Math.abs(objetivo-hover)<0.01f) {hover=objetivo; interaccion.stop();}
                capa.repaint();
            });
            interaccion.start();
        }
        @Override public void paint(Graphics graphics, JComponent componente) {
            Graphics2D g=(Graphics2D)graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setComposite(AlphaComposite.SrcOver.derive(Math.max(0, Math.min(1,alpha))));
                double escala=hover<0 ? 1+hover*0.035 : 1;
                g.translate((1-escala)*componente.getWidth()/2,
                        desplazamiento + (hover>0 ? -2*hover : 0)+(1-escala)*componente.getHeight()/2);
                g.scale(escala,escala);
                super.paint(g,componente);
                if (hover>0) {
                    g.setColor(new Color(255,188,13,Math.round(hover*150)));
                    g.setStroke(new BasicStroke(1.5f));
                    g.drawRoundRect(2,2,Math.max(0,componente.getWidth()-5),
                            Math.max(0,componente.getHeight()-5),18,18);
                }
            } finally {g.dispose();}
        }
    }
}
