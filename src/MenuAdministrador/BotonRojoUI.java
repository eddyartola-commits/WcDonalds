package MenuAdministrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/** Paleta roja exclusiva de los botones administrativos de Pagos. */
public class BotonRojoUI extends BasicButtonUI {
    @Override public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton b=(AbstractButton)c;
        b.setOpaque(false);b.setContentAreaFilled(false);b.setBorderPainted(false);
        b.setFocusPainted(false);b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI",Font.BOLD,13));
        b.setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
        b.setRolloverEnabled(true);
    }
    @Override public void paint(Graphics graphics,JComponent component) {
        AbstractButton b=(AbstractButton)component;
        Graphics2D g=(Graphics2D)graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            boolean presionado=b.getModel().isPressed();
            Color inicio=presionado?new Color(115,14,33):new Color(206,45,67);
            Color fin=presionado?new Color(86,10,24):new Color(133,19,41);
            if(!b.isEnabled()){inicio=new Color(202,172,179);fin=new Color(167,140,149);}
            else if(b.getModel().isRollover()){inicio=new Color(224,58,79);fin=new Color(158,25,47);}
            g.setPaint(new GradientPaint(0,0,inicio,component.getWidth(),component.getHeight(),fin));
            g.fillRoundRect(1,1,component.getWidth()-2,component.getHeight()-2,20,20);
            if(b.hasFocus()) {g.setColor(new Color(255,220,225));g.setStroke(new BasicStroke(2));
                g.drawRoundRect(3,3,component.getWidth()-7,component.getHeight()-7,17,17);}
            super.paint(g,component);
        } finally {g.dispose();}
    }
}
