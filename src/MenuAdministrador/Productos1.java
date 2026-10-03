package MenuAdministrador;

import java.awt.Component;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class Productos1 extends JPanel {
private CrudProductos crud;
private Integer categoriaActual = null;
private Conexion.FiltroProductosDAO.Grupo grupoActual = null;

public void mostrarCategoria(Integer idCategoria) {
    grupoActual = null;
    categoriaActual = idCategoria;
    if (crud != null) crud.contexto(categoriaActual, grupoActual);
    cargarProductosTabla();
}

    public void mostrarGrupo(Conexion.FiltroProductosDAO.Grupo grupo) {
        if (grupo == null) {
            mostrarCategoria(null);
            return;
        }
        grupoActual = grupo;
        categoriaActual = null;
        if (crud != null) crud.contexto(categoriaActual, grupoActual);
        cargarProductosTabla();
    }

    private int versionCarga = 0;
    public Productos1() {
        initComponents();

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        tabla1.setOpaque(false);

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        botonVerdeUsuario1.setText("CREAR PRODUCTO");
        if (!java.beans.Beans.isDesignTime()) {
            crud = new CrudProductos(this, tabla1,
                new javax.swing.JComponent[]{texboxtUsuarios1,texboxtUsuarios2,texboxtUsuarios3,texboxtUsuarios4,texboxtUsuarios5,texboxtUsuarios6,texboxtUsuarios7},
                jPanel11,jPanel12,PanelOpciones,buscador1,
                new javax.swing.JButton[]{botonVerdeUsuario1,botonCafe1,boton4,botonAmarillo1,botonAmarillo2});
            Nombre5.setText("                  Categoría");
            cargarProductosTabla();
        }
    }

    public void cargarProductosTabla() {
        final Integer categoriaConsulta = categoriaActual;
        final Conexion.FiltroProductosDAO.Grupo grupoConsulta = grupoActual;
        final int cargaActual = ++versionCarga;
        if (tabla1.isEditing()) {
            tabla1.getCellEditor().cancelCellEditing();
        }

     String[] colsProductos = {
            "", "ID Producto", "Nombre", "Descripcion",
            "Precio", "Imagen", "Categoria",
            "Disponible", "Acciones"
        };

        int[] anchosProductos = {
            40, 70, 120, 160, 100, 120, 100, 100, 110
        };

        tabla1.configurarColumnas(colsProductos, anchosProductos);

        DefaultTableModel modelo = (DefaultTableModel) tabla1.getModel();
        modelo.setRowCount(0);

        // Renderizador ultra optimizado: escalea la imagen ÚNICAMENTE cuando la fila aparece en pantalla
        tabla1.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            private final java.util.Map<String, ImageIcon> cacheImagenes = new java.util.HashMap<>();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);

                if (value instanceof String) {
                    String ruta = (String) value;

                    if (ruta.trim().isEmpty()) {
                        lbl.setIcon(null);
                        return lbl;
                    }

                    // Si la imagen ya está en memoria caché, la muestra al instante
                    if (cacheImagenes.containsKey(ruta)) {
                        lbl.setIcon(cacheImagenes.get(ruta));
                    } else {
                        // Carga y escalea SOLO la imagen visible en pantalla
                        java.awt.image.BufferedImage img = ImagenProducto.cargar(ruta);
                        if (img != null) {
                            ImageIcon iconFinal = new ImageIcon(escalarImagenRapida(img,55,55));
                            cacheImagenes.put(ruta,iconFinal); lbl.setIcon(iconFinal);
                        } else { lbl.setIcon(null); lbl.setText("Sin imagen"); }

                    }
                } else if (value instanceof ImageIcon) {
                    lbl.setIcon((ImageIcon) value);
                } else {
                    lbl.setIcon(null);
                }
                return lbl;
            }
        });

        // Hilo secundario rápido solo para traer las filas de texto de la BD (sin procesar imágenes)
        javax.swing.SwingWorker<java.util.List<Object[]>, Void> worker = new javax.swing.SwingWorker<java.util.List<Object[]>, Void>() {
            @Override
            protected java.util.List<Object[]> doInBackground() throws Exception {
                if (grupoConsulta != null) {
                    return new Conexion.FiltroProductosDAO().obtenerTabla(grupoConsulta);
                }
                Conexion.ProductoDAO dao = new Conexion.ProductoDAO();
                return categoriaConsulta == null
                        ? dao.obtenerProductosTabla()
                        : dao.obtenerProductosTablaPorCategoria(categoriaConsulta);
            }

            @Override
            protected void done() {
                if (cargaActual != versionCarga) return;
                try {
                    java.util.List<Object[]> lista = get();
                    if (lista != null) {
                        for (Object[] fila : lista) {
                            modelo.addRow(fila); // Agrega filas de texto inmediatamente
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Throwable causa = e.getCause() != null ? e.getCause() : e;
                    javax.swing.JOptionPane.showMessageDialog(Productos1.this,
                            "No se pudieron cargar los productos: " + causa.getMessage(),
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }
    
    // Método para escalar las imágenes rápido con Graphics2D en lugar de SCALE_SMOOTH
    private Image escalarImagenRapida(Image srcImg, int w, int h) {
        java.awt.image.BufferedImage resizedImg = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int iw=srcImg.getWidth(null),ih=srcImg.getHeight(null);
        if(iw>0&&ih>0){double f=Math.min((double)w/iw,(double)h/ih);int nw=Math.max(1,(int)(iw*f)),nh=Math.max(1,(int)(ih*f));g2.drawImage(srcImg,(w-nw)/2,(h-nh)/2,nw,nh,null);}
        g2.dispose();

        return resizedImg;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        PanelUsuarios = new javax.swing.JPanel();
        PanelContenedor = new Componentes.PanelRedondeadoSombra();
        jScrollPane2 = new javax.swing.JScrollPane();
        PanelOpciones = new javax.swing.JPanel();
        PanelEstado = new javax.swing.JPanel();
        Nombre6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        labelEscalable10 = new Labels.LabelEscalable();
        texboxtUsuarios7 = new Componentes.TexboxtUsuarios();
        PanelTotal = new javax.swing.JPanel();
        Nombre5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        labelEscalable9 = new Labels.LabelEscalable();
        texboxtUsuarios6 = new Componentes.TexboxtUsuarios();
        PanelDescuento = new javax.swing.JPanel();
        Nombre4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        labelEscalable8 = new Labels.LabelEscalable();
        texboxtUsuarios5 = new Componentes.TexboxtUsuarios();
        PanelCorreo = new javax.swing.JPanel();
        Nombre3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        labelEscalable6 = new Labels.LabelEscalable();
        texboxtUsuarios4 = new Componentes.TexboxtUsuarios();
        PanelClave = new javax.swing.JPanel();
        Nombre2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        labelEscalable5 = new Labels.LabelEscalable();
        texboxtUsuarios3 = new Componentes.TexboxtUsuarios();
        PanelUsuario = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelEscalable4 = new Labels.LabelEscalable();
        texboxtUsuarios2 = new Componentes.TexboxtUsuarios();
        PanelNombre = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        labelEscalable3 = new Labels.LabelEscalable();
        texboxtUsuarios1 = new Componentes.TexboxtUsuarios();
        Nombre = new javax.swing.JLabel();
        boton4 = new Componentes.boton();
        botonAmarillo1 = new Componentes.BotonCaca();
        botonCafe1 = new Componentes.BotonCafe();
        botonVerdeUsuario1 = new Componentes.BotonVerdeUsuario();
        botonAmarillo2 = new Componentes.BotonCaca();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        jPanel1 = new javax.swing.JPanel();
        CabeceraCentral = new Componentes.PanelRedondeadoSombra();
        jPanel9 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelEscalable7 = new Labels.LabelEscalable();
        ContenedorBuscador = new javax.swing.JPanel();
        buscador1 = new Componentes.Buscador();
        ContedorTabla = new Componentes.PanelRedondeadoSombra();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla1 = new Componentes.Tabla();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        PanelUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        PanelUsuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        PanelUsuarios.setPreferredSize(new java.awt.Dimension(550, 100));
        PanelUsuarios.setLayout(new java.awt.BorderLayout());

        PanelContenedor.setBackground(new java.awt.Color(255, 255, 255));
        PanelContenedor.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        PanelOpciones.setBackground(new java.awt.Color(204, 204, 0));
        PanelOpciones.setOpaque(false);
        PanelOpciones.setLayout(new java.awt.GridBagLayout());

        PanelEstado.setBackground(new java.awt.Color(255, 204, 204));
        PanelEstado.setOpaque(false);
        PanelEstado.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelEstado.setLayout(new java.awt.BorderLayout());

        Nombre6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre6.setText("                  Disponible");
        PanelEstado.add(Nombre6, java.awt.BorderLayout.NORTH);

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel12.setLayout(new java.awt.BorderLayout());

        labelEscalable10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable10.setText("labelEscalable3");
        labelEscalable10.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel12.add(labelEscalable10, java.awt.BorderLayout.WEST);

        texboxtUsuarios7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios7.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel12.add(texboxtUsuarios7, java.awt.BorderLayout.CENTER);

        PanelEstado.add(jPanel12, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelEstado, gridBagConstraints);

        PanelTotal.setBackground(new java.awt.Color(255, 204, 204));
        PanelTotal.setOpaque(false);
        PanelTotal.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelTotal.setLayout(new java.awt.BorderLayout());

        Nombre5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre5.setText("                  ID Categoria");
        PanelTotal.add(Nombre5, java.awt.BorderLayout.NORTH);

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel11.setLayout(new java.awt.BorderLayout());

        labelEscalable9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable9.setText("labelEscalable3");
        labelEscalable9.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel11.add(labelEscalable9, java.awt.BorderLayout.WEST);

        texboxtUsuarios6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios6.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel11.add(texboxtUsuarios6, java.awt.BorderLayout.CENTER);

        PanelTotal.add(jPanel11, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelTotal, gridBagConstraints);

        PanelDescuento.setBackground(new java.awt.Color(255, 204, 204));
        PanelDescuento.setOpaque(false);
        PanelDescuento.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelDescuento.setLayout(new java.awt.BorderLayout());

        Nombre4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre4.setText("                  Imagen Path");
        PanelDescuento.add(Nombre4, java.awt.BorderLayout.NORTH);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel10.setLayout(new java.awt.BorderLayout());

        labelEscalable8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable8.setText("labelEscalable3");
        labelEscalable8.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel10.add(labelEscalable8, java.awt.BorderLayout.WEST);

        texboxtUsuarios5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios5.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel10.add(texboxtUsuarios5, java.awt.BorderLayout.CENTER);

        PanelDescuento.add(jPanel10, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelDescuento, gridBagConstraints);

        PanelCorreo.setBackground(new java.awt.Color(255, 204, 204));
        PanelCorreo.setOpaque(false);
        PanelCorreo.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelCorreo.setLayout(new java.awt.BorderLayout());

        Nombre3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre3.setText("                  Precio");
        PanelCorreo.add(Nombre3, java.awt.BorderLayout.NORTH);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel8.setLayout(new java.awt.BorderLayout());

        labelEscalable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable6.setText("labelEscalable3");
        labelEscalable6.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel8.add(labelEscalable6, java.awt.BorderLayout.WEST);

        texboxtUsuarios4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios4.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel8.add(texboxtUsuarios4, java.awt.BorderLayout.CENTER);

        PanelCorreo.add(jPanel8, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelCorreo, gridBagConstraints);

        PanelClave.setBackground(new java.awt.Color(255, 204, 204));
        PanelClave.setOpaque(false);
        PanelClave.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelClave.setLayout(new java.awt.BorderLayout());

        Nombre2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre2.setText("                  Descripcion");
        PanelClave.add(Nombre2, java.awt.BorderLayout.NORTH);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel7.setLayout(new java.awt.BorderLayout());

        labelEscalable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable5.setText("labelEscalable3");
        labelEscalable5.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel7.add(labelEscalable5, java.awt.BorderLayout.WEST);

        texboxtUsuarios3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios3.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel7.add(texboxtUsuarios3, java.awt.BorderLayout.CENTER);

        PanelClave.add(jPanel7, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelClave, gridBagConstraints);

        PanelUsuario.setBackground(new java.awt.Color(255, 204, 204));
        PanelUsuario.setOpaque(false);
        PanelUsuario.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelUsuario.setLayout(new java.awt.BorderLayout());

        Nombre1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre1.setText("                  Nombre");
        PanelUsuario.add(Nombre1, java.awt.BorderLayout.NORTH);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel6.setLayout(new java.awt.BorderLayout());

        labelEscalable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable4.setText("labelEscalable3");
        labelEscalable4.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel6.add(labelEscalable4, java.awt.BorderLayout.WEST);

        texboxtUsuarios2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios2.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel6.add(texboxtUsuarios2, java.awt.BorderLayout.CENTER);

        PanelUsuario.add(jPanel6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 15, 6, 15);
        PanelOpciones.add(PanelUsuario, gridBagConstraints);

        PanelNombre.setBackground(new java.awt.Color(255, 204, 204));
        PanelNombre.setOpaque(false);
        PanelNombre.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelNombre.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel5.setLayout(new java.awt.BorderLayout());

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        labelEscalable3.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel5.add(labelEscalable3, java.awt.BorderLayout.WEST);

        texboxtUsuarios1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        texboxtUsuarios1.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel5.add(texboxtUsuarios1, java.awt.BorderLayout.CENTER);

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre.setText("                  ID Producto");
        jPanel5.add(Nombre, java.awt.BorderLayout.PAGE_START);

        PanelNombre.add(jPanel5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(23, 15, 6, 15);
        PanelOpciones.add(PanelNombre, gridBagConstraints);

        boton4.setText("BORRAR");
        boton4.setPreferredSize(new java.awt.Dimension(430, 65));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        PanelOpciones.add(boton4, gridBagConstraints);

        botonAmarillo1.setText("LIMPIAR");
        botonAmarillo1.setPreferredSize(new java.awt.Dimension(430, 65));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        PanelOpciones.add(botonAmarillo1, gridBagConstraints);

        botonCafe1.setText("ACTUALIZAR");
        botonCafe1.setPreferredSize(new java.awt.Dimension(430, 65));
        botonCafe1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCafe1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        PanelOpciones.add(botonCafe1, gridBagConstraints);

        botonVerdeUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        botonVerdeUsuario1.setText("CREAR USUARIO");
        botonVerdeUsuario1.setPreferredSize(new java.awt.Dimension(430, 65));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        PanelOpciones.add(botonVerdeUsuario1, gridBagConstraints);

        botonAmarillo2.setText("BUSCAR");
        botonAmarillo2.setPreferredSize(new java.awt.Dimension(430, 65));
        botonAmarillo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAmarillo2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        PanelOpciones.add(botonAmarillo2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weighty = 1.0;
        PanelOpciones.add(filler1, gridBagConstraints);

        jScrollPane2.setViewportView(PanelOpciones);

        PanelContenedor.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        PanelUsuarios.add(PanelContenedor, java.awt.BorderLayout.CENTER);

        add(PanelUsuarios, java.awt.BorderLayout.WEST);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        CabeceraCentral.setBackground(new java.awt.Color(255, 255, 255));
        CabeceraCentral.setPreferredSize(new java.awt.Dimension(0, 130));
        CabeceraCentral.setLayout(new javax.swing.BoxLayout(CabeceraCentral, javax.swing.BoxLayout.Y_AXIS));

        jPanel9.setBackground(new java.awt.Color(255, 102, 51));
        jPanel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 30, 0, 30));
        jPanel9.setOpaque(false);
        jPanel9.setPreferredSize(new java.awt.Dimension(0, 60));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 10));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        labelEscalable7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable7.setText("labelEscalable7");
        labelEscalable7.setPreferredSize(new java.awt.Dimension(95, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(labelEscalable7, gridBagConstraints);

        jPanel9.add(jPanel2, java.awt.BorderLayout.WEST);

        ContenedorBuscador.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 1, 25, 5));
        ContenedorBuscador.setOpaque(false);
        ContenedorBuscador.setPreferredSize(new java.awt.Dimension(500, 100));
        ContenedorBuscador.setLayout(new java.awt.BorderLayout());

        buscador1.setPlaceholder("Buscar productos");
        buscador1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscador1ActionPerformed(evt);
            }
        });
        ContenedorBuscador.add(buscador1, java.awt.BorderLayout.CENTER);

        jPanel9.add(ContenedorBuscador, java.awt.BorderLayout.EAST);

        CabeceraCentral.add(jPanel9);

        jPanel1.add(CabeceraCentral, java.awt.BorderLayout.NORTH);

        ContedorTabla.setBackground(new java.awt.Color(255, 255, 255));
        ContedorTabla.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(tabla1);

        ContedorTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(ContedorTabla, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }//GEN-END:initComponents
    // </editor-fold>                        

    private void buscador1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscador1ActionPerformed
        if (crud != null) crud.filtrar();
    }//GEN-LAST:event_buscador1ActionPerformed

    private void botonAmarillo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAmarillo2ActionPerformed

    private void botonCafe1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCafe1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonCafe1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.PanelRedondeadoSombra CabeceraCentral;
    private Componentes.PanelRedondeadoSombra ContedorTabla;
    private javax.swing.JPanel ContenedorBuscador;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JLabel Nombre2;
    private javax.swing.JLabel Nombre3;
    private javax.swing.JLabel Nombre4;
    private javax.swing.JLabel Nombre5;
    private javax.swing.JLabel Nombre6;
    private javax.swing.JPanel PanelClave;
    private Componentes.PanelRedondeadoSombra PanelContenedor;
    private javax.swing.JPanel PanelCorreo;
    private javax.swing.JPanel PanelDescuento;
    private javax.swing.JPanel PanelEstado;
    private javax.swing.JPanel PanelNombre;
    private javax.swing.JPanel PanelOpciones;
    private javax.swing.JPanel PanelTotal;
    private javax.swing.JPanel PanelUsuario;
    private javax.swing.JPanel PanelUsuarios;
    private Componentes.boton boton4;
    private Componentes.BotonCaca botonAmarillo1;
    private Componentes.BotonCaca botonAmarillo2;
    private Componentes.BotonCafe botonCafe1;
    private Componentes.BotonVerdeUsuario botonVerdeUsuario1;
    private Componentes.Buscador buscador1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private Labels.LabelEscalable labelEscalable10;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    private Labels.LabelEscalable labelEscalable5;
    private Labels.LabelEscalable labelEscalable6;
    private Labels.LabelEscalable labelEscalable7;
    private Labels.LabelEscalable labelEscalable8;
    private Labels.LabelEscalable labelEscalable9;
    private Componentes.Tabla tabla1;
    private Componentes.TexboxtUsuarios texboxtUsuarios1;
    private Componentes.TexboxtUsuarios texboxtUsuarios2;
    private Componentes.TexboxtUsuarios texboxtUsuarios3;
    private Componentes.TexboxtUsuarios texboxtUsuarios4;
    private Componentes.TexboxtUsuarios texboxtUsuarios5;
    private Componentes.TexboxtUsuarios texboxtUsuarios6;
    private Componentes.TexboxtUsuarios texboxtUsuarios7;
    // End of variables declaration//GEN-END:variables

}
