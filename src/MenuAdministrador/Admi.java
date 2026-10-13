
package MenuAdministrador;

import java.awt.CardLayout;

public class Admi extends javax.swing.JFrame {

    private CardLayout cardLayout;
    // Referencias a las vistas (inicialmente null para que carguen rápido)
    private Usuarios panelUsuarios;
    private Productos1 panelProductos;
    private Categorias panelCategorias;
    private Pagos panelPagos;
    private Ventas panelVentas;

    public Admi() {
        initComponents();
        configurarEstilo();
        this.setExtendedState(Admi.MAXIMIZED_BOTH);

        cardLayout = (CardLayout) panelCentro.getLayout();

        // Cargar por defecto ÚNICAMENTE el panel de Usuarios
        mostrarPanelUsuarios();
        
        if (Usuario != null) {
            Usuario.setSelected(true);
        }
    }

    
    // Métodos con Lazy Loading (Solo cargan una vez cuando el usuario da clic)
    private void mostrarPanelUsuarios() {
        if (panelUsuarios == null) {
            panelUsuarios = new Usuarios();
            panelCentro.add(panelUsuarios, "PANEL_USUARIOS");
        }
        mostrarConTransicion("PANEL_USUARIOS");
        seleccionarMenu(Usuario);
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    private void mostrarPanelProductos() {
        mostrarProductosPorCategoria(null);
    }

    public void mostrarProductosPorCategoria(Integer idCategoria) {
        if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
            javax.swing.SwingUtilities.invokeLater(
                    () -> mostrarProductosPorCategoria(idCategoria));
            return;
        }

        try {
            if (panelProductos == null) {
                panelProductos = new Productos1();
                panelCentro.add(panelProductos, "PANEL_PRODUCTOS");
                // El constructor ya carga todos los productos.
                if (idCategoria != null) {
                    panelProductos.mostrarCategoria(idCategoria);
                }
            } else {
                panelProductos.mostrarCategoria(idCategoria);
            }

            mostrarConTransicion("PANEL_PRODUCTOS");
            seleccionarMenu(Producto);
            panelCentro.revalidate();
            panelCentro.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    ex.toString(), "Error al abrir Productos",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarGrupoProductos(Conexion.FiltroProductosDAO.Grupo grupo) {
        if (!javax.swing.SwingUtilities.isEventDispatchThread()) {
            javax.swing.SwingUtilities.invokeLater(() -> mostrarGrupoProductos(grupo));
            return;
        }
        try {
            if (panelProductos == null) {
                panelProductos = new Productos1();
                panelCentro.add(panelProductos, "PANEL_PRODUCTOS");
            }
            panelProductos.mostrarGrupo(grupo);
            mostrarConTransicion("PANEL_PRODUCTOS");
            seleccionarMenu(Producto);
            panelCentro.revalidate();
            panelCentro.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, ex.toString(),
                    "Error al abrir Productos", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarMenu(Componentes.BotonAdmi seleccionado) {
        Componentes.BotonAdmi[] botones = {
            Usuario, Pagos, Ventas, Producto, botonAdmi3
        };
        for (Componentes.BotonAdmi boton : botones) {
            boton.setSelected(boton == seleccionado);
        }
    }

    private void mostrarPanelCategorias() {
        if (panelCategorias == null) {
            panelCategorias = new Categorias();
            panelCentro.add(panelCategorias, "PANEL_CATEGORIAS");
        }
        panelCategorias.actualizarCantidades();
        mostrarConTransicion("PANEL_CATEGORIAS");
        seleccionarMenu(botonAdmi3);
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    private void mostrarPanelVentas() {
        if (panelVentas == null) {
            panelVentas = new Ventas();
            panelCentro.add(panelVentas, "PANEL_VENTAS");
        }
        mostrarConTransicion("PANEL_VENTAS");
        seleccionarMenu(Ventas);
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    private void mostrarPanelPagos() {
        if (panelPagos == null) {
            panelPagos = new Pagos();
            panelCentro.add(panelPagos, "PANEL_PAGOS");
        }
        mostrarConTransicion("PANEL_PAGOS");
        seleccionarMenu(Pagos);
        panelCentro.revalidate();
        panelCentro.repaint();
    }
    private String vistaActual;
    private final Transicion transicion = new Transicion();

    private void configurarEstilo() {
        setTitle("WcDonalds | Administración");
        setMinimumSize(new java.awt.Dimension(1000, 650));
        java.awt.Color rojo = new java.awt.Color(173, 8, 15);
        cabecera.setBackground(rojo);
        cabecera.setPreferredSize(new java.awt.Dimension(0, 90));
        cabecera.setBorder(javax.swing.BorderFactory.createMatteBorder(
                0, 0, 3, 0, new java.awt.Color(255, 188, 13)));
        labelEscalable1.setPreferredSize(new java.awt.Dimension(245, 80));
        SubPanelCabecera.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 6, 19));
        panelPerfil1.setPreferredSize(new java.awt.Dimension(200, 80));
        botonPerfil1.setBounds(8, 15, 184, 54);
        for (Componentes.BotonAdmi b : new Componentes.BotonAdmi[]{
                Usuario, Pagos, Ventas, Producto, botonAdmi3}) {
            b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
            b.setPreferredSize(new java.awt.Dimension(112, 46));
            b.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        botonAdmi3.setText("Categorías");
        panelCentro.setBackground(new java.awt.Color(248, 249, 251));
        panelCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 14, 14, 14));
        setGlassPane(transicion);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowOpened(java.awt.event.WindowEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> animarEntrada());
            }
        });
    }

    private java.awt.image.BufferedImage capturarCentro() {
        if (panelCentro.getWidth() <= 0 || panelCentro.getHeight() <= 0) return null;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                panelCentro.getWidth(), panelCentro.getHeight(),
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = img.createGraphics();
        try { panelCentro.printAll(g); } finally { g.dispose(); }
        return img;
    }

    private String destinoPendiente;
    private boolean salidaUsuarios;

    private void mostrarConTransicion(String nombre) {
        if (salidaUsuarios) { destinoPendiente = nombre; return; }
        if (("PANEL_USUARIOS".equals(vistaActual) || "PANEL_PAGOS".equals(vistaActual) || "PANEL_VENTAS".equals(vistaActual) || "PANEL_PRODUCTOS".equals(vistaActual))
                && !nombre.equals(vistaActual) && isShowing()) {
            destinoPendiente = nombre;
            salidaUsuarios = true;
            Runnable fin = () -> {
                salidaUsuarios = false;
                cambiarVista(destinoPendiente);
            };
            if ("PANEL_PAGOS".equals(vistaActual)) panelPagos.animarSalida(fin);
            else if ("PANEL_VENTAS".equals(vistaActual)) panelVentas.animarSalida(fin);
            else if ("PANEL_PRODUCTOS".equals(vistaActual)) panelProductos.animarSalida(fin);
            else panelUsuarios.animarSalida(fin);
        } else cambiarVista(nombre);
    }

    private void cambiarVista(String nombre) {
        boolean cambiar = vistaActual != null && !nombre.equals(vistaActual) && isShowing();
        transicion.detener();
        java.awt.image.BufferedImage anterior = cambiar ? capturarCentro() : null;
        cardLayout.show(panelCentro, nombre);
        vistaActual = nombre;
        panelCentro.revalidate();
        panelCentro.doLayout();
        panelCentro.repaint();
        if ("PANEL_USUARIOS".equals(nombre)) {
            seleccionarMenu(Usuario);
            if (isShowing()) panelUsuarios.animarEntrada();
        } else if ("PANEL_CATEGORIAS".equals(nombre)) {
            seleccionarMenu(botonAdmi3);
            if(isShowing())panelCategorias.animarEntrada();
        } else if ("PANEL_PRODUCTOS".equals(nombre)) {
            seleccionarMenu(Producto);
            if (isShowing()) panelProductos.animarEntrada();
        } else if ("PANEL_VENTAS".equals(nombre)) {
            seleccionarMenu(Ventas);
            if (isShowing()) panelVentas.animarEntrada();
        } else if ("PANEL_PAGOS".equals(nombre)) {
            seleccionarMenu(Pagos);
            if (isShowing()) panelPagos.animarEntrada();
        } else {
            seleccionarMenu("PANEL_PRODUCTOS".equals(nombre) ? Producto
                    : "PANEL_CATEGORIAS".equals(nombre) ? botonAdmi3
                    : "PANEL_PAGOS".equals(nombre) ? Pagos : Ventas);
            if (anterior != null) transicion.iniciar(anterior, capturarCentro());
        }
    }

    private void animarEntrada() {
        panelCentro.doLayout();
        if ("PANEL_USUARIOS".equals(vistaActual)) panelUsuarios.animarEntrada();
        else if ("PANEL_PAGOS".equals(vistaActual)) panelPagos.animarEntrada();
        else if ("PANEL_VENTAS".equals(vistaActual)) panelVentas.animarEntrada();
        else if ("PANEL_PRODUCTOS".equals(vistaActual)) panelProductos.animarEntrada();
        else if ("PANEL_CATEGORIAS".equals(vistaActual)) panelCategorias.animarEntrada();
        else transicion.iniciar(null, capturarCentro());
    }

    @Override public void dispose() {
        transicion.detener();
        super.dispose();
    }

    // La capa solo cubre el contenido: el menú sigue disponible durante la transición.
    private class Transicion extends javax.swing.JComponent {
        private java.awt.image.BufferedImage anterior, nueva;
        private javax.swing.Timer timer;
        private long inicio;
        private float progreso;
        private java.awt.Rectangle area;

        void iniciar(java.awt.image.BufferedImage antes, java.awt.image.BufferedImage despues) {
            detener();
            if (despues == null) return;
            anterior = antes;
            nueva = despues;
            java.awt.Point punto = javax.swing.SwingUtilities.convertPoint(
                    panelCentro, 0, 0, this);
            area = new java.awt.Rectangle(punto.x, punto.y,
                    panelCentro.getWidth(), panelCentro.getHeight());
            progreso = 0;
            inicio = System.nanoTime();
            setVisible(true);
            timer = new javax.swing.Timer(16, e -> {
                progreso = Math.min(1f, (System.nanoTime() - inicio) / 280000000f);
                repaint();
                if (progreso >= 1f) detener();
            });
            timer.start();
        }

        void detener() {
            if (timer != null) timer.stop();
            setVisible(false);
            anterior = null;
            nueva = null;
        }

        @Override public boolean contains(int x, int y) {
            return isVisible() && area != null && area.contains(x, y);
        }

        @Override protected void paintComponent(java.awt.Graphics graphics) {
            if (nueva == null || area == null) return;
            java.awt.Graphics2D g = (java.awt.Graphics2D) graphics.create();
            try {
                g.clip(area);
                g.setColor(panelCentro.getBackground());
                g.fill(area);
                float suave = 1f - (float) Math.pow(1f - progreso, 3);
                if (anterior != null) {
                    g.setComposite(java.awt.AlphaComposite.SrcOver.derive(1f - suave));
                    g.drawImage(anterior, area.x - Math.round(18 * suave), area.y, null);
                }
                g.setComposite(java.awt.AlphaComposite.SrcOver.derive(suave));
                g.drawImage(nueva, area.x + Math.round(30 * (1f - suave)), area.y, null);
            } finally { g.dispose(); }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cabecera = new javax.swing.JPanel();
        labelEscalable1 = new Labels.LabelEscalable();
        SubPanelCabecera = new javax.swing.JPanel();
        Usuario = new Componentes.BotonAdmi();
        Pagos = new Componentes.BotonAdmi();
        Ventas = new Componentes.BotonAdmi();
        Producto = new Componentes.BotonAdmi();
        botonAdmi3 = new Componentes.BotonAdmi();
        panelPerfil1 = new javax.swing.JPanel();
        botonPerfil1 = new Componentes.BotonPerfil();
        panelCentro = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/WCicono.png")).getImage());

        cabecera.setBackground(new java.awt.Color(173, 8, 15));
        cabecera.setMaximumSize(new java.awt.Dimension(10, 10));
        cabecera.setPreferredSize(new java.awt.Dimension(0, 100));
        cabecera.setLayout(new java.awt.BorderLayout());

        labelEscalable1.setBackground(new java.awt.Color(173, 8, 15));
        labelEscalable1.setForeground(new java.awt.Color(173, 8, 15));
        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (7).png"))); // NOI18N
        labelEscalable1.setPreferredSize(new java.awt.Dimension(375, 80));
        cabecera.add(labelEscalable1, java.awt.BorderLayout.WEST);

        SubPanelCabecera.setBackground(new java.awt.Color(173, 8, 15));
        SubPanelCabecera.setForeground(new java.awt.Color(173, 8, 15));
        SubPanelCabecera.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 27));

        Usuario.setText("Usuarios");
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });
        SubPanelCabecera.add(Usuario);

        Pagos.setText("Pagos");
        Pagos.setToolTipText("");
        Pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PagosActionPerformed(evt);
            }
        });
        SubPanelCabecera.add(Pagos);

        Ventas.setText("Ventas");
        Ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasActionPerformed(evt);
            }
        });
        SubPanelCabecera.add(Ventas);

        Producto.setText("Productos");
        Producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductoActionPerformed(evt);
            }
        });
        SubPanelCabecera.add(Producto);

        botonAdmi3.setText("Categoria");
        botonAdmi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdmi3ActionPerformed(evt);
            }
        });
        SubPanelCabecera.add(botonAdmi3);

        cabecera.add(SubPanelCabecera, java.awt.BorderLayout.CENTER);

        panelPerfil1.setBackground(new java.awt.Color(173, 8, 15));
        panelPerfil1.setPreferredSize(new java.awt.Dimension(300, 100));
        panelPerfil1.setLayout(null);
        panelPerfil1.add(botonPerfil1);
        botonPerfil1.setBounds(50, 20, 220, 60);

        cabecera.add(panelPerfil1, java.awt.BorderLayout.EAST);

        getContentPane().add(cabecera, java.awt.BorderLayout.NORTH);

        panelCentro.setBackground(new java.awt.Color(255, 255, 255));
        panelCentro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 216, 216), 1, true));
        panelCentro.setPreferredSize(new java.awt.Dimension(20, 20));
        panelCentro.setLayout(new java.awt.CardLayout());
        getContentPane().add(panelCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // </editor-fold>                        

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        mostrarPanelUsuarios();
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuarioActionPerformed

    private void ProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductoActionPerformed
        mostrarPanelProductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductoActionPerformed

    private void botonAdmi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdmi3ActionPerformed

    System.out.println("Clic en Categoría");

    try {
        mostrarPanelCategorias();
        panelCentro.revalidate();
        panelCentro.repaint();
    } catch (Exception ex) {
        ex.printStackTrace();

        javax.swing.JOptionPane.showMessageDialog(
            this,
            ex.toString(),
            "Error al abrir Categorías",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_botonAdmi3ActionPerformed

    private void VentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasActionPerformed
        mostrarPanelVentas();
        // TODO add your handling code here:
    }//GEN-LAST:event_VentasActionPerformed

    private void PagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PagosActionPerformed

        mostrarPanelPagos();
        // TODO add your handling code here:
    }//GEN-LAST:event_PagosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.BotonAdmi Pagos;
    private Componentes.BotonAdmi Producto;
    private javax.swing.JPanel SubPanelCabecera;
    private Componentes.BotonAdmi Usuario;
    private Componentes.BotonAdmi Ventas;
    private Componentes.BotonAdmi botonAdmi3;
    private Componentes.BotonPerfil botonPerfil1;
    private javax.swing.JPanel cabecera;
    private Labels.LabelEscalable labelEscalable1;
    private javax.swing.JPanel panelCentro;
    private javax.swing.JPanel panelPerfil1;
    // End of variables declaration//GEN-END:variables
}
