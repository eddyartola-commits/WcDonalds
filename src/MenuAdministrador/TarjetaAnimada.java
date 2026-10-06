package MenuAdministrador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Conserva la tarjeta real y sus botones; anima únicamente su presentación. */
public final class TarjetaAnimada extends JPanel {
    private final JPanel tarjeta;
    private final int indice;
    private final Timer reloj;
    private float alpha=1f, entrada=0f, hover=0f, destinoHover=0f;
    private long inicio;
    private boolean entrando;

    public TarjetaAnimada(JPanel tarjeta,int indice){
        this.tarjeta=tarjeta;this.indice=indice;
        setOpaque(false);setLayout(null);tarjeta.setBorder(null);add(tarjeta);
        reloj=new Timer(16,e->actualizar());
        escuchar(this);
        addHierarchyListener(e->{
            if((e.getChangeFlags()&HierarchyEvent.SHOWING_CHANGED)!=0){
                if(isShowing())aparecer();else detener();
            }
        });
    }
    private void escuchar(Component c){
        c.addMouseListener(new MouseAdapter(){
            @Override public void mouseEntered(MouseEvent e){resaltar(true);}
            @Override public void mouseExited(MouseEvent e){
                Point p=SwingUtilities.convertPoint(e.getComponent(),e.getPoint(),TarjetaAnimada.this);
                if(!contains(p))resaltar(false);
            }
        });
        c.addFocusListener(new FocusAdapter(){
            @Override public void focusGained(FocusEvent e){resaltar(true);}
            @Override public void focusLost(FocusEvent e){resaltar(false);}
        });
        if(c instanceof Container)for(Component hijo:((Container)c).getComponents())escuchar(hijo);
    }
    public void aparecer(){
        inicio=System.nanoTime();entrando=true;alpha=0;entrada=20;
        hover=0;destinoHover=0;reloj.start();repaint();
    }
    public void resaltar(boolean activo){destinoHover=activo?1:0;if(!reloj.isRunning())reloj.start();}
    public void detener(){reloj.stop();entrando=false;alpha=1;entrada=0;hover=0;destinoHover=0;doLayout();repaint();}
    private void actualizar(){
        if(entrando){
            double transcurrido=(System.nanoTime()-inicio)/1000000.0-Math.min(indice*55,440);
            double t=Math.max(0,Math.min(1,transcurrido/460.0));
            double suave=1-Math.pow(1-t,3);alpha=(float)suave;entrada=(float)(20*(1-suave));
            if(t>=1)entrando=false;
        }
        hover+=(destinoHover-hover)*0.18f;
        if(Math.abs(destinoHover-hover)<0.002f)hover=destinoHover;
        doLayout();repaint();
        if(!entrando&&hover==destinoHover)reloj.stop();
    }
    @Override public void doLayout(){
        if(tarjeta!=null)tarjeta.setBounds(12,12-Math.round(hover*9),Math.max(1,getWidth()-24),Math.max(1,getHeight()-24));
    }
    @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D p=(Graphics2D)g.create();
        p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        p.setComposite(AlphaComposite.SrcOver.derive(alpha));
        int y=12-Math.round(hover*9)+Math.round(entrada);
        int w=Math.max(1,getWidth()-24),h=Math.max(1,getHeight()-24);
        for(int radio=8;radio>=1;radio--){
            p.setColor(new Color(35,18,20,Math.round(3+hover*6)));
            p.fillRoundRect(12-radio,y+4-radio/2,w+radio*2,h+radio,30+radio,30+radio);
        }
        p.dispose();
    }
    @Override protected void paintChildren(Graphics g){
        Graphics2D p=(Graphics2D)g.create();
        p.setComposite(AlphaComposite.SrcOver.derive(alpha));p.translate(0,entrada);
        Rectangle r=tarjeta.getBounds();
        java.awt.geom.RoundRectangle2D forma=new java.awt.geom.RoundRectangle2D.Float(r.x,r.y,r.width,r.height,30,30);
        Graphics2D contenido=(Graphics2D)p.create();
        contenido.clip(forma);super.paintChildren(contenido);contenido.dispose();
        p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        p.setStroke(new BasicStroke(1f+hover));
        p.setColor(new Color(189,8,28,Math.round(18+hover*105)));
        p.draw(new java.awt.geom.RoundRectangle2D.Float(r.x+1,r.y+1,Math.max(1,r.width-2),Math.max(1,r.height-2),30,30));
        p.dispose();
    }
    @Override public void removeNotify(){detener();super.removeNotify();}
}
