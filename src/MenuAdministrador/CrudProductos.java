package MenuAdministrador;

import Conexion.ProductoDAO;
import Conexion.TarjetasDAO;
import Modelo.Producto;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.table.*;

/** Conecta el formulario lateral y los botones de la tabla con MySQL. */
public class CrudProductos {
    private final Productos1 vista;
    private final Componentes.Tabla tabla;
    private final ProductoDAO dao=new ProductoDAO();
    private final JTextComponent[] campos=new JTextComponent[5];
    private final JComboBox<Opcion> categorias=new JComboBox<>(),subs=new JComboBox<>();
    private final JCheckBox disponible=new JCheckBox("Disponible para venta",true);
    private final JComponent buscador;
    private final JButton[] botones;
    private final List<Object[]> subcategorias=new ArrayList<>();
    private boolean ocupado=false,listo=false;
    private int versionSeleccion=0;
    private Integer idSeleccionado=null;
    private String rutaOriginal="";
    private int versionOpciones=0;
    private Conexion.FiltroProductosDAO.Grupo grupo;
    private Integer categoriaFiltro;
    private TableRowSorter<TableModel> sorter;
    private static class Opcion {int id;String nombre;Opcion(int i,String n){id=i;nombre=n;}public String toString(){return nombre;}}
    public CrudProductos(Productos1 v,Componentes.Tabla t,JComponent[] textos,JPanel categoriaHost,JPanel disponibilidadHost,JPanel opciones,JComponent busca,JButton[] acciones) {
        vista=v;tabla=t;buscador=busca;botones=acciones;
        for(int i=0;i<5;i++){campos[i]=encontrarTexto(textos[i]);if(campos[i]==null)throw new IllegalStateException("No se encontró el campo de texto "+(i+1));}
        campos[0].setEditable(true);campos[0].setToolTipText("Escribe el ID y pulsa Enter o Buscar. Al crear, el ID es automático.");
        if(campos[0] instanceof JTextField)((JTextField)campos[0]).addActionListener(e->buscar());
        campos[0].getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            private void cambio(){++versionSeleccion;if(idSeleccionado!=null&&!campos[0].getText().trim().equals(String.valueOf(idSeleccionado)))idSeleccionado=null;}
            public void insertUpdate(javax.swing.event.DocumentEvent e){cambio();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){cambio();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){cambio();}
        });
        JTextComponent textoBusca=encontrarTexto(buscador);
        if(textoBusca!=null)textoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            public void insertUpdate(javax.swing.event.DocumentEvent e){filtrar();}
            public void removeUpdate(javax.swing.event.DocumentEvent e){filtrar();}
            public void changedUpdate(javax.swing.event.DocumentEvent e){filtrar();}
        });
        disponible.setOpaque(false);categoriaHost.remove(textos[5]);categoriaHost.add(categorias,BorderLayout.CENTER);disponibilidadHost.remove(textos[6]);disponibilidadHost.add(disponible,BorderLayout.CENTER);
        categorias.addActionListener(e->actualizarSubs());
        GridBagLayout layout=(GridBagLayout)opciones.getLayout();for(Component c:opciones.getComponents()){GridBagConstraints g=layout.getConstraints(c);if(g.gridy>=7){g.gridy++;layout.setConstraints(c,g);}}
        JPanel panel=new JPanel(new BorderLayout(4,4));panel.setOpaque(false);panel.add(new JLabel("Subcategoría"),BorderLayout.NORTH);panel.add(subs,BorderLayout.CENTER);panel.setPreferredSize(new Dimension(200,70));GridBagConstraints g=new GridBagConstraints();g.gridx=0;g.gridy=7;g.fill=GridBagConstraints.HORIZONTAL;g.weightx=1;g.insets=new Insets(6,25,6,25);opciones.add(panel,g);
        // Crear, actualizar, borrar, limpiar y buscar.
        acciones[0].addActionListener(e->guardar(true));acciones[1].addActionListener(e->guardar(false));acciones[2].addActionListener(e->borrar());acciones[3].addActionListener(e->limpiar());acciones[4].addActionListener(e->buscar());
        campos[4].setToolTipText("Doble clic para elegir imagen, o escribe una ruta del proyecto.");
        campos[4].addMouseListener(new java.awt.event.MouseAdapter(){public void mouseClicked(java.awt.event.MouseEvent e){if(e.getClickCount()==2)elegirImagen();}});
        tabla.setAccionEditar(fila->seleccionarId(idFila(fila)));
        tabla.setAccionEliminar(fila->{int id=idFila(fila);if(id>0)eliminar(vista,id,()->{limpiar();vista.cargarProductosTabla();});});
        tabla.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()&&!ocupado){int f=tabla.getSelectedRow();if(f>=0)seleccionarId(idFila(tabla.convertRowIndexToModel(f)));}});
        tabla.addPropertyChangeListener("model",e->modeloCambiado());modeloCambiado();habilitar(false);cargarOpciones();
    }
    private static JTextComponent encontrarTexto(Component c){if(c instanceof JTextComponent)return(JTextComponent)c;if(c instanceof Container)for(Component x:((Container)c).getComponents()){JTextComponent t=encontrarTexto(x);if(t!=null)return t;}return null;}
    public void modeloCambiado(){sorter=new TableRowSorter<>(tabla.getModel());tabla.setRowSorter(sorter);filtrar();}
    public void filtrar(){if(sorter==null)return;JTextComponent b=encontrarTexto(buscador);String texto=b==null?"":b.getText().trim();sorter.setRowFilter(texto.isEmpty()?null:RowFilter.regexFilter("(?iu)"+java.util.regex.Pattern.quote(texto),1,2,3,6));}
    public void contexto(Integer categoria,Conexion.FiltroProductosDAO.Grupo grupo){this.categoriaFiltro=categoria;this.grupo=grupo;limpiar();cargarOpciones();}
    private int idFila(int f){if(f<0||f>=tabla.getModel().getRowCount())return-1;Object id=tabla.getModel().getValueAt(f,1);return id instanceof Number?((Number)id).intValue():-1;}
    private void habilitar(boolean b){for(JButton x:botones)x.setEnabled(b);for(JTextComponent x:campos)x.setEnabled(b);categorias.setEnabled(b);subs.setEnabled(b);disponible.setEnabled(b);}
    private void cargarOpciones(){final int carga=++versionOpciones;listo=false;habilitar(false);new SwingWorker<List<List<Object[]>>,Void>(){protected List<List<Object[]>> doInBackground()throws Exception{List<List<Object[]>>r=new ArrayList<>();r.add(dao.categoriasCrud());r.add(dao.subcategoriasCrud());return r;}protected void done(){if(carga!=versionOpciones)return;try{List<List<Object[]>> r=get();subcategorias.clear();categorias.removeAllItems();subcategorias.addAll(r.get(1));for(Object[] x:r.get(0))categorias.addItem(new Opcion((Integer)x[0],(String)x[1]));listo=true;habilitar(true);limpiar();}catch(Exception e){error(vista,e);}}}.execute();}
    private void actualizarSubs(){subs.removeAllItems();subs.addItem(new Opcion(0,"Sin subcategoría"));Opcion c=(Opcion)categorias.getSelectedItem();if(c!=null)for(Object[] s:subcategorias)if(c.id==(Integer)s[2])subs.addItem(new Opcion((Integer)s[0],(String)s[1]));}
    private void elegir(JComboBox<Opcion> combo,Integer id){if(id==null)return;for(int i=0;i<combo.getItemCount();i++)if(combo.getItemAt(i).id==id){combo.setSelectedIndex(i);return;}}
    public void limpiar(){JTextComponent b=encontrarTexto(buscador);if(b!=null)b.setText("");filtrar();++versionSeleccion;idSeleccionado=null;rutaOriginal="";for(JTextComponent c:campos)c.setText("");disponible.setSelected(true);if(!listo)return;
        Integer cat=categoriaFiltro,sub=0;
        if(grupo!=null)switch(grupo){case HAMBURGUESAS:cat=1;sub=3;break;case POLLO:cat=1;sub=4;break;case CAJITA_FELIZ:cat=1;sub=1;break;case BEBIDAS:cat=3;break;case POSTRES:cat=7;break;case DESAYUNOS:cat=4;break;case PAPAS:cat=2;break;default:break;}
        if(cat==null&&categorias.getItemCount()>0)categorias.setSelectedIndex(0);else elegir(categorias,cat);elegir(subs,sub);
    }
    private void buscar(){
        String texto=campos[0].getText().trim();
        if(!texto.isEmpty()){
            try{int id=Integer.parseInt(texto);if(id<1)throw new NumberFormatException();seleccionarId(id,true);}
            catch(NumberFormatException e){JOptionPane.showMessageDialog(vista,"Escribe un ID entero mayor que cero.");}
            return;
        }
        JTextComponent b=encontrarTexto(buscador);
        if(b!=null&&b.getText().trim().isEmpty())b.setText(campos[1].getText().trim());
        filtrar();
    }
    private void seleccionarId(int id){seleccionarId(id,false);}
    private void seleccionarId(int id,boolean filtrarId){if(id<1||ocupado||!listo)return;idSeleccionado=null;final int version=++versionSeleccion;new SwingWorker<Producto,Void>(){protected Producto doInBackground()throws Exception{return dao.obtenerProductoPorId(id);}protected void done(){if(version!=versionSeleccion)return;try{Producto p=get();if(p==null){idSeleccionado=null;JOptionPane.showMessageDialog(vista,"El producto ya no existe.");vista.cargarProductosTabla();return;}idSeleccionado=null;rutaOriginal=p.getImagenPath()==null?"":p.getImagenPath();campos[0].setText(String.valueOf(id));idSeleccionado=id;campos[1].setText(p.getNombre());campos[2].setText(p.getDescripcion()==null?"":p.getDescripcion());campos[3].setText(BigDecimal.valueOf(p.getPrecio()).setScale(2,java.math.RoundingMode.HALF_UP).toPlainString());campos[4].setText(p.getImagenPath()==null?"":p.getImagenPath());elegir(categorias,p.getIdCategoria());elegir(subs,p.getIdSubcategoria()==null?0:p.getIdSubcategoria());disponible.setSelected(p.isDisponible());
                if(filtrarId){
                    JTextComponent b=encontrarTexto(buscador);if(b!=null)b.setText("");
                    if(sorter!=null)sorter.setRowFilter(new RowFilter<TableModel,Integer>(){
                        @Override public boolean include(RowFilter.Entry<? extends TableModel,? extends Integer> fila){
                            Object valor=fila.getValue(1);
                            return valor instanceof Number?((Number)valor).intValue()==id:String.valueOf(id).equals(String.valueOf(valor).trim());
                        }
                    });
                }
            }catch(Exception e){error(vista,e);}}}.execute();}
    private void elegirImagen(){JFileChooser f=new JFileChooser();f.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes","png","jpg","jpeg"));if(f.showOpenDialog(vista)==JFileChooser.APPROVE_OPTION){String p=f.getSelectedFile().getAbsolutePath();if(ImagenProducto.cargar(p)==null)JOptionPane.showMessageDialog(vista,"Imagen inválida.");else campos[4].setText(p);}}
    private void guardar(boolean nuevo){if(ocupado||!listo)return;if(!nuevo&&idSeleccionado==null){JOptionPane.showMessageDialog(vista,"Selecciona un producto en la tabla o busca su ID antes de continuar.");return;}
        Producto p=new Producto();p.setIdProducto(nuevo?0:idSeleccionado);p.setNombre(campos[1].getText().trim());p.setDescripcion(campos[2].getText().trim());p.setImagenPath(campos[4].getText().trim());Opcion c=(Opcion)categorias.getSelectedItem(),s=(Opcion)subs.getSelectedItem();if(c==null){JOptionPane.showMessageDialog(vista,"Selecciona una categoría.");return;}p.setIdCategoria(c.id);p.setIdSubcategoria(s==null||s.id==0?null:s.id);p.setDisponible(disponible.isSelected());
        BigDecimal precio;
        try{precio=new BigDecimal(campos[3].getText().trim().replace(',','.'));if(precio.signum()<0||precio.scale()>2||precio.compareTo(new BigDecimal("99999999.99"))>0)throw new NumberFormatException();}catch(NumberFormatException e){JOptionPane.showMessageDialog(vista,"Escribe un precio válido con máximo dos decimales.");return;}
        if(p.getNombre().isEmpty()||p.getNombre().length()>100||p.getDescripcion().length()>255||p.getImagenPath().length()>255){JOptionPane.showMessageDialog(vista,"Nombre: 1-100 caracteres; descripción y ruta: máximo 255.");return;}
        if(!p.getImagenPath().isEmpty()&&(nuevo||!p.getImagenPath().equals(rutaOriginal))&&ImagenProducto.cargar(p.getImagenPath())==null){JOptionPane.showMessageDialog(vista,"No se encuentra la imagen. Corrige la ruta o deja el campo vacío.");return;}
        final String extra=grupo==null?null:grupo.name();final int guardado=++versionSeleccion;ocupado=true;habilitar(false);
        new SwingWorker<Integer,Void>(){protected Integer doInBackground()throws Exception {
            java.nio.file.Path copia=null;String original=p.getImagenPath();
            // Copia solo imágenes externas. Las rutas src/... ya pertenecen al proyecto.
            if(!original.isEmpty()&&new java.io.File(original).isAbsolute()&&new java.io.File(original).isFile()) {
                java.nio.file.Path raiz=java.nio.file.Paths.get(System.getProperty("user.dir"));boolean proyecto=java.nio.file.Files.isDirectory(raiz.resolve("src"));java.nio.file.Path dir=proyecto?raiz.resolve("src/ImagenesProductos"):java.nio.file.Paths.get(System.getProperty("user.home"),"WcDonalds","imagenes-productos");java.nio.file.Files.createDirectories(dir);int punto=original.lastIndexOf('.');String ext=punto>=0?original.substring(punto):".png";copia=dir.resolve(java.util.UUID.randomUUID()+ext);java.nio.file.Files.copy(java.nio.file.Paths.get(original),copia);p.setImagenPath(proyecto?"src/ImagenesProductos/"+copia.getFileName():copia.toString());
            }
            try{return dao.guardarProducto(p,precio,nuevo,extra);}catch(Exception e){if(copia!=null)java.nio.file.Files.deleteIfExists(copia);throw e;}
        }protected void done(){ocupado=false;habilitar(true);try{int id=get();if(guardado==versionSeleccion)limpiar();vista.cargarProductosTabla();JOptionPane.showMessageDialog(vista,"Producto #"+id+(nuevo?" creado.":" actualizado."));}catch(Exception e){error(vista,e);}}}.execute();
    }
    private void borrar(){if(idSeleccionado==null){JOptionPane.showMessageDialog(vista,"Selecciona un producto en la tabla o busca su ID antes de continuar.");return;}eliminar(vista,idSeleccionado,()->{limpiar();vista.cargarProductosTabla();});}
    public static void eliminar(JComponent padre,int id,Runnable despues){if(JOptionPane.showConfirmDialog(padre,"Eliminar permanentemente el producto #"+id+"?","Confirmar eliminación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)!=JOptionPane.YES_OPTION)return;
        new SwingWorker<Boolean,Void>(){protected Boolean doInBackground()throws Exception{return new ProductoDAO().eliminarProducto(id);}protected void done(){try{boolean borrado=get();JOptionPane.showMessageDialog(padre,borrado?"Producto eliminado.":"El producto ya no existe.");despues.run();}catch(Exception e){Throwable causa=e;while(causa.getCause()!=null)causa=causa.getCause();if(causa instanceof java.sql.SQLException&&((java.sql.SQLException)causa).getErrorCode()==1451){if(JOptionPane.showConfirmDialog(padre,"El producto está usado en pedidos y no se puede eliminar. ¿Quieres desactivarlo para la venta?","Producto con historial",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)desactivar(padre,id,despues);}else error(padre,e);}}}.execute();
    }
    private static void desactivar(JComponent padre,int id,Runnable despues){new SwingWorker<Boolean,Void>(){protected Boolean doInBackground()throws Exception{return new ProductoDAO().cambiarDisponibilidadProducto(id,false);}protected void done(){try{JOptionPane.showMessageDialog(padre,get()?"Producto desactivado.":"Producto no encontrado.");despues.run();}catch(Exception e){error(padre,e);}}}.execute();}
    public static void eliminarTarjeta(JComponent padre,TarjetasDAO.Tarjeta t,Runnable despues){new SwingWorker<List<Object[]>,Void>(){protected List<Object[]> doInBackground()throws Exception{return new TarjetasDAO().productos(t);}protected void done(){try{List<Object[]> filas=get();if(filas.isEmpty()){JOptionPane.showMessageDialog(padre,"Este grupo no tiene productos.");return;}DefaultListModel<String> m=new DefaultListModel<>();for(Object[] f:filas)m.addElement("#"+f[0]+" - "+f[1]);JList<String> l=new JList<>(m);l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);JScrollPane s=new JScrollPane(l);s.setPreferredSize(new Dimension(500,300));if(JOptionPane.showConfirmDialog(padre,s,"Elegir producto de "+t.nombre+" para eliminar",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION&&l.getSelectedIndex()>=0)eliminar(padre,(Integer)filas.get(l.getSelectedIndex())[0],despues);}catch(Exception e){error(padre,e);}}}.execute();}
    private static void error(JComponent padre,Exception ex){Throwable e=ex;while(e.getCause()!=null)e=e.getCause();ex.printStackTrace();JOptionPane.showMessageDialog(padre,e.getMessage(),"No se pudo completar",JOptionPane.ERROR_MESSAGE);}
}
