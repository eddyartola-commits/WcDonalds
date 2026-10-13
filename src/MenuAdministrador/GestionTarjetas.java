package MenuAdministrador;

import Conexion.TarjetasDAO;
import Conexion.TarjetasDAO.Tarjeta;
import java.awt.*;
import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

/** Controlador de las tarjetas; los componentes originales siguen en el .form. */
public class GestionTarjetas {
    private final Categorias vista;
    private final TarjetasDAO dao=new TarjetasDAO();
    private final List<Item> items=new ArrayList<>();
    private final JPanel contenido=new JPanel(null);
    private final JScrollPane scroll=new JScrollPane(contenido);
    private final JTextField buscar;
    private int orden=0,filtro=0,version=0;
    private boolean lista=false,ocupado=false;
    private final JButton[] controles;
    private static class Item {
        Tarjeta dato; TarjetaAnimada capa; JPanel panel; JLabel imagen,nombre,cantidad; JButton abrir,borrar;
        Item(Tarjeta t,JPanel p,JLabel i,JLabel n,JLabel c,JButton a,JButton b){dato=t;panel=p;imagen=i;nombre=n;cantidad=c;abrir=a;borrar=b;}
    }
    public GestionTarjetas(Categorias v,JPanel[] paneles,JLabel[] imagenes,JLabel[] nombres,JLabel[] cantidades,JButton[] amarillos,JButton[] rojos,JTextField busqueda,JButton filtrar,JButton agregar,JButton[] menu) {
        vista=v;buscar=busqueda;controles=new JButton[]{menu[1],menu[2],menu[3],agregar};
        String[] claves={"HAMBURGUESAS","POLLO","BEBIDAS","POSTRES","COMBOS","DESAYUNOS","PAPAS","CAJITA_FELIZ"};
        for(int i=0;i<claves.length;i++) {
            Item it=new Item(new Tarjeta(claves[i],nombres[i].getText(),null,null),paneles[i],imagenes[i],nombres[i],cantidades[i],amarillos[i],rojos[i]);
            items.add(it);it.capa=new TarjetaAnimada(it.panel,i);contenido.add(it.capa);
            amarillos[i].setToolTipText("Abrir productos de "+it.dato.nombre);
            amarillos[i].addActionListener(e->abrir(it));
            rojos[i].setToolTipText("Elegir un producto de " + it.dato.nombre + " para eliminar");
            rojos[i].addActionListener(e -> { if(lista) CrudProductos.eliminarTarjeta(vista,it.dato,()->cargar()); });
        }
        scroll.setBorder(null);scroll.getVerticalScrollBar().setUnitIncrement(24);vista.add(scroll);
        buscar.setText("");buscar.setToolTipText("Buscar categoría por nombre");
        buscar.getDocument().addDocumentListener(new DocumentListener(){public void insertUpdate(DocumentEvent e){refrescar();}public void removeUpdate(DocumentEvent e){refrescar();}public void changedUpdate(DocumentEvent e){refrescar();}});
        filtrar.addActionListener(e->filtrar());agregar.addActionListener(e->agregar());
        String[] textos={"Ordenar","Asignar productos","Disponibilidad","Exportar catálogo"};
        for(int i=0;i<menu.length;i++){menu[i].setText(textos[i]);menu[i].setIcon(null);menu[i].setToolTipText(textos[i]);}
        menu[0].addActionListener(e->ordenar());menu[1].addActionListener(e->asignar());menu[2].addActionListener(e->disponibilidad());menu[3].addActionListener(e->exportar());
        habilitar(false);
    }
    public void animarEntrada(){for(Item i:items)if(i.capa.isVisible())i.capa.aparecer();}
    private void habilitar(boolean b){for(JButton c:controles)c.setEnabled(b);}
    private void refrescar(){vista.revalidate();vista.repaint();}
    public void cargar() {
        final int carga=++version;lista=false;habilitar(false);
        new SwingWorker<List<Tarjeta>,Void>() {
            protected List<Tarjeta> doInBackground()throws Exception {
                dao.preparar();List<Tarjeta> r=new ArrayList<>();
                // Copia solo datos; no accede a componentes Swing desde el hilo de consulta.
                String[] claves={"HAMBURGUESAS","POLLO","BEBIDAS","POSTRES","COMBOS","DESAYUNOS","PAPAS","CAJITA_FELIZ"};
                for(String c:claves){Tarjeta t=new Tarjeta(c,c,null,null);t.cantidad=dao.contar(t);r.add(t);}
                for(Tarjeta t:dao.nuevas()){t.cantidad=dao.contar(t);r.add(t);}return r;
            }
            protected void done(){if(carga!=version)return;try {
                List<Tarjeta> datos=get();
                for(int i=items.size()-1;i>=8;i--)contenido.remove(items.remove(i).capa);
                for(int i=0;i<8;i++){items.get(i).dato.cantidad=datos.get(i).cantidad;items.get(i).cantidad.setText(datos.get(i).cantidad+" productos");}
                for(int i=8;i<datos.size();i++)crearVisual(datos.get(i));lista=true;habilitar(!ocupado);refrescar();
            }catch(Exception e){error(e);}}
        }.execute();
    }
    private void crearVisual(Tarjeta t) {
        JPanel p=new JPanel(null);p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        JLabel im=new JLabel(),n=new JLabel(t.nombre,SwingConstants.CENTER),c=new JLabel(t.cantidad+" productos",SwingConstants.CENTER);
        n.setFont(new Font("Segoe UI",Font.BOLD,17));
        if(t.imagen!=null&&!t.imagen.isEmpty()){ImageIcon ic=new ImageIcon(t.imagen);if(ic.getIconWidth()>0)im.putClientProperty("imagenOriginal",ic);}
        JButton a=new JButton("Abrir"),b=new JButton("Eliminar");a.setBackground(new Color(255,188,13));b.setBackground(new Color(190,0,20));b.setForeground(Color.WHITE);
        // Eliminar categorías requiere decidir qué hacer con sus productos. Se conserva pendiente.
        b.setToolTipText("Elegir producto para eliminar");
        b.addActionListener(e -> { if(lista) CrudProductos.eliminarTarjeta(vista,t,()->cargar()); });
        Item it=new Item(t,p,im,n,c,a,b);items.add(it);
        for(Component x:new Component[]{im,n,c,a,b})p.add(x);
        a.addActionListener(e->abrir(it));
        java.awt.event.MouseAdapter clic=new java.awt.event.MouseAdapter(){public void mouseClicked(java.awt.event.MouseEvent e){if(SwingUtilities.isLeftMouseButton(e))abrir(it);}};
        for(JComponent x:new JComponent[]{p,im,n,c}){x.addMouseListener(clic);x.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}
        it.capa=new TarjetaAnimada(p,items.size()-1);contenido.add(it.capa);
    }
    public void layout(int x,int y,int w,int h) {
        scroll.setBounds(x,y,Math.max(1,w),Math.max(1,h));
        List<Item> visibles=new ArrayList<>();String texto=normalizar(buscar.getText());
        for(Item i:items){boolean ok=normalizar(i.dato.nombre).contains(texto)&&(filtro==0||(filtro==1?i.dato.cantidad>0:i.dato.cantidad==0));i.panel.setVisible(ok);i.capa.setVisible(ok);if(ok)visibles.add(i);}
        if(orden==1)visibles.sort(Comparator.comparing(i->normalizar(i.dato.nombre)));
        if(orden==2)visibles.sort(Comparator.comparingInt((Item i)->i.dato.cantidad).reversed().thenComparing(i->normalizar(i.dato.nombre)));
        int ancho=Math.max(1,w-22),cols=Math.max(1,Math.min(4,(ancho+16)/240));
        int tw=Math.max(1,(ancho-16*(cols-1))/cols),th=Math.max(270,(h-16)/2);
        for(int k=0;k<visibles.size();k++) {
            Item i=visibles.get(k);i.panel.setLayout(null);i.capa.setBounds((k%cols)*(tw+16),(k/cols)*(th+16),tw,th);i.capa.doLayout();
            int cw=Math.max(1,tw-24),ch=Math.max(1,th-24);
            int by=ch-55,ny=by-55;
            i.imagen.setBounds(10,10,Math.max(1,cw-20),Math.max(1,ny-15));escalar(i.imagen);
            i.nombre.setHorizontalAlignment(SwingConstants.CENTER);i.nombre.setBounds(5,ny,cw-10,25);i.cantidad.setHorizontalAlignment(SwingConstants.CENTER);i.cantidad.setVerticalAlignment(SwingConstants.CENTER);i.cantidad.setBounds(5,ny+25,cw-10,25);
            boolean nueva=i.dato.categoria!=null;int bw=nueva?85:45;
            i.abrir.setBounds(cw/2-bw-6,by,bw,45);i.borrar.setBounds(cw/2+6,by,bw,45);
        }
        int filas=(visibles.size()+cols-1)/cols;contenido.setPreferredSize(new Dimension(ancho,Math.max(1,filas*(th+16)-16)));contenido.revalidate();
    }
    private void escalar(JLabel l){ImageIcon ic=(ImageIcon)l.getClientProperty("imagenOriginal");if(ic==null&&l.getIcon() instanceof ImageIcon){ic=(ImageIcon)l.getIcon();l.putClientProperty("imagenOriginal",ic);}if(ic==null||ic.getIconWidth()<1)return;
        Dimension d=l.getSize();if(d.equals(l.getClientProperty("gestionMedida")))return;l.putClientProperty("gestionMedida",d);double f=Math.min((double)d.width/ic.getIconWidth(),(double)d.height/ic.getIconHeight());l.setIcon(new ImageIcon(ic.getImage().getScaledInstance(Math.max(1,(int)(ic.getIconWidth()*f)),Math.max(1,(int)(ic.getIconHeight()*f)),Image.SCALE_SMOOTH)));l.setHorizontalAlignment(SwingConstants.CENTER);}
    private static String normalizar(String s){return Normalizer.normalize(s==null?"":s,Normalizer.Form.NFD).replaceAll("\\p{M}","").toLowerCase(java.util.Locale.ROOT);}
    private void abrir(Item i) {
        Window w=SwingUtilities.getWindowAncestor(vista);
        if(!(w instanceof Admi)){JOptionPane.showMessageDialog(vista,"Abre Categorías desde Administrador.");return;}
        if(i.dato.categoria!=null)((Admi)w).mostrarProductosPorCategoria(i.dato.categoria);
        else ((Admi)w).mostrarGrupoProductos(Conexion.FiltroProductosDAO.Grupo.valueOf(i.dato.clave));
    }
    private void ordenar(){String[] op={"Orden original","Nombre A-Z","Mayor cantidad de productos"};Object r=JOptionPane.showInputDialog(vista,"Orden de las tarjetas","Ordenar",JOptionPane.QUESTION_MESSAGE,null,op,op[orden]);if(r!=null){orden=java.util.Arrays.asList(op).indexOf(r);refrescar();}}
    private void filtrar(){String[] op={"Todas","Con productos","Sin productos"};Object r=JOptionPane.showInputDialog(vista,"Combina este filtro con el texto de búsqueda","Filtrar",JOptionPane.QUESTION_MESSAGE,null,op,op[filtro]);if(r!=null){filtro=java.util.Arrays.asList(op).indexOf(r);refrescar();}}
    private Tarjeta elegir(String titulo){if(!lista){JOptionPane.showMessageDialog(vista,"Espera a que carguen las categorías.");return null;}Tarjeta[] ts=items.stream().map(i->i.dato).toArray(Tarjeta[]::new);return(Tarjeta)JOptionPane.showInputDialog(vista,"Selecciona el grupo",titulo,JOptionPane.QUESTION_MESSAGE,null,ts,ts[0]);}
    private void agregar() {
        JTextField nombre=new JTextField(22),imagen=new JTextField(22);imagen.setEditable(false);JButton elegir=new JButton("Elegir PNG/JPG");
        elegir.addActionListener(e->{JFileChooser f=new JFileChooser();f.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes","png","jpg","jpeg"));if(f.showOpenDialog(vista)==JFileChooser.APPROVE_OPTION){File a=f.getSelectedFile();if(new ImageIcon(a.getPath()).getIconWidth()<1)JOptionPane.showMessageDialog(vista,"La imagen no es válida");else imagen.setText(a.getAbsolutePath());}});
        JPanel p=new JPanel(new GridLayout(0,1,4,4));p.add(new JLabel("Nombre de la nueva categoría (máximo 50 caracteres)"));p.add(nombre);p.add(new JLabel("Imagen opcional; se copiará a tu carpeta de usuario"));p.add(imagen);p.add(elegir);
        if(JOptionPane.showConfirmDialog(vista,p,"Agregar tarjeta",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION)return;
        String n=nombre.getText().trim();if(n.isEmpty()||n.length()>50){JOptionPane.showMessageDialog(vista,"Escribe un nombre de 1 a 50 caracteres.");return;}
        for(Item it:items) if(normalizar(it.dato.nombre).equals(normalizar(n))){JOptionPane.showMessageDialog(vista,"Ya existe una tarjeta con ese nombre.");return;}
        String origen=imagen.getText();tarea(()->{String destino="";java.nio.file.Path copia=null;
            if(!origen.isEmpty()){java.nio.file.Path dir=java.nio.file.Paths.get(System.getProperty("user.home"),"WcDonalds","imagenes-categorias");java.nio.file.Files.createDirectories(dir);String ext=origen.substring(origen.lastIndexOf('.'));copia=dir.resolve(java.util.UUID.randomUUID()+ext);java.nio.file.Files.copy(java.nio.file.Paths.get(origen),copia);destino=copia.toString();}
            try{dao.crear(n,destino);}catch(Exception e){if(copia!=null)java.nio.file.Files.deleteIfExists(copia);throw e;}return "Categoría guardada. Usa Asignar productos para llenarla.";});
    }
    private interface Trabajo{String ejecutar()throws Exception;}
    private void tarea(Trabajo trabajo){if(ocupado)return;ocupado=true;habilitar(false);new SwingWorker<String,Void>(){protected String doInBackground()throws Exception{return trabajo.ejecutar();}protected void done(){ocupado=false;try{JOptionPane.showMessageDialog(vista,get());cargar();}catch(Exception e){habilitar(lista);error(e);}}}.execute();}
    private void asignar(){Tarjeta t=elegir("Asignar productos");if(t==null)return;
        new SwingWorker<List<Object[]>,Void>(){protected List<Object[]> doInBackground()throws Exception{return dao.productos(null);}protected void done(){try{seleccionarProductos(t,get());}catch(Exception e){error(e);}}}.execute();
    }
    private void seleccionarProductos(Tarjeta t,List<Object[]> filas) {
        DefaultListModel<String> modelo=new DefaultListModel<>();JList<String> listaProd=new JList<>(modelo);listaProd.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JTextField filtroNombre=new JTextField();List<Object[]> visibles=new ArrayList<>();Runnable actualizar=()->{modelo.clear();visibles.clear();for(Object[] f:filas)if(normalizar((String)f[1]).contains(normalizar(filtroNombre.getText()))){modelo.addElement("#"+f[0]+" - "+f[1]+" | "+f[4]);visibles.add(f);}};actualizar.run();
        filtroNombre.getDocument().addDocumentListener(new DocumentListener(){public void insertUpdate(DocumentEvent e){actualizar.run();}public void removeUpdate(DocumentEvent e){actualizar.run();}public void changedUpdate(DocumentEvent e){actualizar.run();}});
        JPanel p=new JPanel(new BorderLayout(4,4));p.add(filtroNombre,BorderLayout.NORTH);p.add(new JScrollPane(listaProd),BorderLayout.CENTER);p.add(new JLabel("Ctrl + clic para seleccionar varios productos"),BorderLayout.SOUTH);p.setPreferredSize(new Dimension(620,360));
        if(JOptionPane.showConfirmDialog(vista,p,"Asignar a "+t.nombre,JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION)return;
        List<Integer> ids=new ArrayList<>();for(int idx:listaProd.getSelectedIndices())ids.add((Integer)visibles.get(idx)[0]);if(ids.isEmpty()){JOptionPane.showMessageDialog(vista,"Selecciona al menos un producto.");return;}
        boolean especial=t.clave.equals("PAPAS")||t.clave.equals("COMBOS");String texto=especial?"Añadir "+ids.size()+" productos al grupo "+t.nombre+" conservando su categoría actual?":"Mover "+ids.size()+" productos a "+t.nombre+"? Su categoría y subcategoría actuales cambiarán.";
        if(JOptionPane.showConfirmDialog(vista,texto,"Confirmar asignación",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        tarea(()->{dao.asignar(t,ids);return "Productos asignados.";});
    }
    private void disponibilidad(){Tarjeta t=elegir("Disponibilidad");if(t==null)return;String[] op={"Activar todos","Desactivar todos"};int r=JOptionPane.showOptionDialog(vista,"Cambiar la disponibilidad de los "+t.cantidad+" productos de "+t.nombre+"?","Disponibilidad",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,op,op[0]);if(r<0)return;boolean activo=r==0;tarea(()->dao.disponibilidad(t,activo)+" productos "+(activo?"activados":"desactivados")+".");}
    private void exportar(){Tarjeta t=elegir("Exportar catálogo");if(t==null)return;JFileChooser f=new JFileChooser();f.setSelectedFile(new File("Catalogo-"+t.clave+".pdf"));if(f.showSaveDialog(vista)!=JFileChooser.APPROVE_OPTION)return;File elegido=f.getSelectedFile();File destino=elegido.getName().toLowerCase().endsWith(".pdf")?elegido:new File(elegido.getPath()+".pdf");if(destino.exists()&&JOptionPane.showConfirmDialog(vista,"Reemplazar el PDF existente?","Exportar",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
        tarea(()->{CatalogoPDF.guardar(destino,t.nombre,dao.productos(t));return "PDF guardado en:\n"+destino.getAbsolutePath();});}
    private void error(Exception ex){Throwable e=ex;while(e.getCause()!=null)e=e.getCause();ex.printStackTrace();JOptionPane.showMessageDialog(vista,e.getMessage(),"No se pudo completar",JOptionPane.ERROR_MESSAGE);}
}
