package MenuAdministrador;

import javax.swing.table.DefaultTableModel;
import Conexion.*;

public class Ventas extends javax.swing.JPanel {
public Ventas() {
        initComponents();

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);

        // Llama al método correcto que realiza la consulta SQL
        cargarTablaPedidos();
    }

    public void probarConexionTabla() {
        String[] colsPedidos = {"", "ID Pedido", "Usuario", "Fecha/Hora", "Subtotal", "Descuento", "Total", "Estado", "Acciones"};
        int[] anchosPedidos = {40, 70, 120, 160, 100, 100, 100, 120, 110};

        // Usa tabla1 (con número 1)
        jTable1.configurarColumnas(colsPedidos, anchosPedidos);

        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        VentaDAO dao = new VentaDAO();
        for (Object[] fila : dao.listarVentas()) { 
            modelo.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        PanelUsuarios = new javax.swing.JPanel();
        PanelContenedor = new Componentes.PanelRedondeadoSombra();
        Cabecera = new javax.swing.JPanel();
        labelEscalable2 = new Labels.LabelEscalable();
        jScrollPane2 = new javax.swing.JScrollPane();
        PanelOpciones = new javax.swing.JPanel();
        PanelEstado = new javax.swing.JPanel();
        Nombre6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        labelEscalable10 = new Labels.LabelEscalable();
        txtEstado = new Componentes.TexboxtUsuarios();
        PanelTotal = new javax.swing.JPanel();
        Nombre5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        labelEscalable9 = new Labels.LabelEscalable();
        txtTotal = new Componentes.TexboxtUsuarios();
        PanelDescuento = new javax.swing.JPanel();
        Nombre4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        labelEscalable8 = new Labels.LabelEscalable();
        txtDescuento = new Componentes.TexboxtUsuarios();
        PanelCorreo = new javax.swing.JPanel();
        Nombre3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        labelEscalable6 = new Labels.LabelEscalable();
        txtSubtotal = new Componentes.TexboxtUsuarios();
        PanelClave = new javax.swing.JPanel();
        Nombre2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        labelEscalable5 = new Labels.LabelEscalable();
        txtFecha = new Componentes.TexboxtUsuarios();
        PanelUsuario = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelEscalable4 = new Labels.LabelEscalable();
        txtUsuario = new Componentes.TexboxtUsuarios();
        PanelNombre = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelEscalable3 = new Labels.LabelEscalable();
        txtId = new Componentes.TexboxtUsuarios();
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
        jTable1 = new Componentes.Tabla();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        PanelUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        PanelUsuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
        PanelUsuarios.setPreferredSize(new java.awt.Dimension(550, 100));
        PanelUsuarios.setLayout(new java.awt.BorderLayout());

        PanelContenedor.setBackground(new java.awt.Color(255, 255, 255));
        PanelContenedor.setLayout(new java.awt.BorderLayout());

        Cabecera.setOpaque(false);
        Cabecera.setPreferredSize(new java.awt.Dimension(100, 150));
        Cabecera.setLayout(new java.awt.GridBagLayout());

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Venta.png"))); // NOI18N
        labelEscalable2.setPreferredSize(new java.awt.Dimension(486, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        Cabecera.add(labelEscalable2, gridBagConstraints);

        PanelContenedor.add(Cabecera, java.awt.BorderLayout.NORTH);

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
        Nombre6.setForeground(new java.awt.Color(0, 0, 0));
        Nombre6.setText("                  Estado");
        PanelEstado.add(Nombre6, java.awt.BorderLayout.NORTH);

        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel12.setLayout(new java.awt.BorderLayout());

        labelEscalable10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable10.setText("labelEscalable3");
        labelEscalable10.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel12.add(labelEscalable10, java.awt.BorderLayout.WEST);

        txtEstado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtEstado.setPreferredSize(new java.awt.Dimension(50, 52));
        txtEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoActionPerformed(evt);
            }
        });
        jPanel12.add(txtEstado, java.awt.BorderLayout.CENTER);

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
        Nombre5.setForeground(new java.awt.Color(0, 0, 0));
        Nombre5.setText("                  Total");
        PanelTotal.add(Nombre5, java.awt.BorderLayout.NORTH);

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel11.setLayout(new java.awt.BorderLayout());

        labelEscalable9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable9.setText("labelEscalable3");
        labelEscalable9.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel11.add(labelEscalable9, java.awt.BorderLayout.WEST);

        txtTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtTotal.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel11.add(txtTotal, java.awt.BorderLayout.CENTER);

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
        Nombre4.setForeground(new java.awt.Color(0, 0, 0));
        Nombre4.setText("                  Descuento");
        PanelDescuento.add(Nombre4, java.awt.BorderLayout.NORTH);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel10.setLayout(new java.awt.BorderLayout());

        labelEscalable8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable8.setText("labelEscalable3");
        labelEscalable8.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel10.add(labelEscalable8, java.awt.BorderLayout.WEST);

        txtDescuento.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtDescuento.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel10.add(txtDescuento, java.awt.BorderLayout.CENTER);

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
        Nombre3.setForeground(new java.awt.Color(0, 0, 0));
        Nombre3.setText("                  Subtotal");
        PanelCorreo.add(Nombre3, java.awt.BorderLayout.NORTH);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel8.setLayout(new java.awt.BorderLayout());

        labelEscalable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable6.setText("labelEscalable3");
        labelEscalable6.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel8.add(labelEscalable6, java.awt.BorderLayout.WEST);

        txtSubtotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel8.add(txtSubtotal, java.awt.BorderLayout.CENTER);

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
        Nombre2.setForeground(new java.awt.Color(0, 0, 0));
        Nombre2.setText("                  Fecha");
        PanelClave.add(Nombre2, java.awt.BorderLayout.NORTH);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel7.setLayout(new java.awt.BorderLayout());

        labelEscalable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable5.setText("labelEscalable3");
        labelEscalable5.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel7.add(labelEscalable5, java.awt.BorderLayout.WEST);

        txtFecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtFecha.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel7.add(txtFecha, java.awt.BorderLayout.CENTER);

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
        Nombre1.setForeground(new java.awt.Color(0, 0, 0));
        Nombre1.setText("                  ID Usuario");
        PanelUsuario.add(Nombre1, java.awt.BorderLayout.NORTH);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel6.setLayout(new java.awt.BorderLayout());

        labelEscalable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable4.setText("labelEscalable3");
        labelEscalable4.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel6.add(labelEscalable4, java.awt.BorderLayout.WEST);

        txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtUsuario.setPreferredSize(new java.awt.Dimension(50, 52));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        jPanel6.add(txtUsuario, java.awt.BorderLayout.CENTER);

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

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre.setForeground(new java.awt.Color(0, 0, 0));
        Nombre.setText("                  ID Pedido");
        PanelNombre.add(Nombre, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel5.setLayout(new java.awt.BorderLayout());

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        labelEscalable3.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel5.add(labelEscalable3, java.awt.BorderLayout.WEST);

        txtId.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtId.setPreferredSize(new java.awt.Dimension(50, 52));
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        jPanel5.add(txtId, java.awt.BorderLayout.CENTER);

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
        boton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        PanelOpciones.add(boton4, gridBagConstraints);

        botonAmarillo1.setText("LIMPIAR");
        botonAmarillo1.setPreferredSize(new java.awt.Dimension(430, 65));
        botonAmarillo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAmarillo1ActionPerformed(evt);
            }
        });
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

        botonVerdeUsuario1.setBackground(new java.awt.Color(255, 255, 255));
        botonVerdeUsuario1.setForeground(new java.awt.Color(255, 255, 255));
        botonVerdeUsuario1.setText("CREAR VENTA");
        botonVerdeUsuario1.setActionCommand("CREAR VENTA");
        botonVerdeUsuario1.setPreferredSize(new java.awt.Dimension(430, 65));
        botonVerdeUsuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerdeUsuario1ActionPerformed(evt);
            }
        });
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

        buscador1.setPlaceholder("Buscar Usuarios, Nombres, Correos ...");
        ContenedorBuscador.add(buscador1, java.awt.BorderLayout.CENTER);

        jPanel9.add(ContenedorBuscador, java.awt.BorderLayout.EAST);

        CabeceraCentral.add(jPanel9);

        jPanel1.add(CabeceraCentral, java.awt.BorderLayout.NORTH);

        ContedorTabla.setBackground(new java.awt.Color(255, 255, 255));
        ContedorTabla.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        ContedorTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(ContedorTabla, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botonVerdeUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerdeUsuario1ActionPerformed
                                                  
 // 1. Validar que las casillas requeridas no estén vacías
if (txtUsuario.getText().trim().isEmpty() || 
    txtSubtotal.getText().trim().isEmpty() || 
    txtDescuento.getText().trim().isEmpty() || 
    txtTotal.getText().trim().isEmpty() || 
    txtEstado.getText().trim().isEmpty()) {

    javax.swing.JOptionPane.showMessageDialog(null, "Por favor llena los campos: ID Usuario, Subtotal, Descuento, Total y Estado.");
    return;
}

try {
    // 2. Extraer los valores de las cajas de texto
    int idUsuario = Integer.parseInt(txtUsuario.getText().trim());
    double subtotal = Double.parseDouble(txtSubtotal.getText().trim());
    double descuento = Double.parseDouble(txtDescuento.getText().trim());
    double total = Double.parseDouble(txtTotal.getText().trim());
    String estado = txtEstado.getText().trim().toUpperCase(); // Lee el texto (ej: "PAGADO")

    // 3. Insertar en la base de datos
    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();

    // Se cambió 'PENDIENTE' por el parámetro '?'
    String sql = "INSERT INTO pedidos (id_usuario, fecha_hora, subtotal, descuento, total, estado) VALUES (?, NOW(), ?, ?, ?, ?)";

    java.sql.PreparedStatement pst = con.prepareStatement(sql);
    pst.setInt(1, idUsuario);
    pst.setDouble(2, subtotal);
    pst.setDouble(3, descuento);
    pst.setDouble(4, total);
    pst.setString(5, estado); // Se envía la variable 'estado' como parámetro 5

    int res = pst.executeUpdate();
    
    if (res > 0) {
        javax.swing.JOptionPane.showMessageDialog(null, "¡Pedido creado exitosamente!");
        cargarTablaPedidos(); // Refresca la tabla automáticamente
        
        // Limpiar campos después de guardar
        txtUsuario.setText("");
        txtFecha.setText("");
        txtSubtotal.setText("");
        txtDescuento.setText("");
        txtTotal.setText("");
        txtEstado.setText("");
    }

    pst.close();
    con.close();

} catch (NumberFormatException e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Error de formato: ID Usuario debe ser un número entero y Subtotal, Descuento y Total deben ser números.");
} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Error en la Base de Datos: " + e.getMessage());
}
    }//GEN-LAST:event_botonVerdeUsuario1ActionPerformed

    private void botonAmarillo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo2ActionPerformed
String idTexto = txtId.getText().trim();

    if (idTexto.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de Pedido para buscar.");
        return;
    }

    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();
    String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(idTexto));

        java.sql.ResultSet rs = pst.executeQuery();

        // 1. Obtener el modelo de tu JTable (Cambia 'jTable1' por el variable name de tu tabla)
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        if (rs.next()) {
            // 2. Llenar las casillas de texto
            txtUsuario.setText(rs.getString("id_usuario"));
            txtFecha.setText(rs.getString("fecha_hora"));
            txtSubtotal.setText(rs.getString("subtotal"));
            txtDescuento.setText(rs.getString("descuento"));
            txtTotal.setText(rs.getString("total"));

            // 3. Limpiar las filas actuales de la tabla
            modelo.setRowCount(0);

            // 4. Agregar únicamente el pedido encontrado a la tabla
            Object[] fila = new Object[7];
            fila[0] = rs.getInt("id_pedido");
            fila[1] = rs.getString("id_usuario");
            fila[2] = rs.getString("fecha_hora");
            fila[3] = rs.getDouble("subtotal");
            fila[4] = rs.getDouble("descuento");
            fila[5] = rs.getDouble("total");
            fila[6] = rs.getString("estado");

            modelo.addRow(fila);

            javax.swing.JOptionPane.showMessageDialog(null, "¡Pedido encontrado!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No se encontró ningún pedido con ese ID.");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
    }


    }//GEN-LAST:event_botonAmarillo2ActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void botonCafe1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCafe1ActionPerformed
// 1. Limpiar los campos de texto de la interfaz
    txtId.setText("");
    txtFecha.setText("");
    txtSubtotal.setText("");
    txtDescuento.setText("");
    txtTotal.setText("");
    if (txtUsuario != null) txtUsuario.setText("");
    if (txtEstado != null) txtEstado.setText("");

    // 2. Conectar y consultar todos los pedidos
    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();
    String sql = "SELECT * FROM pedidos ORDER BY id_pedido DESC";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();

        // 3. Obtener el modelo de la tabla
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();

        // 4. Vaciar la tabla completamente antes de volver a cargar
        modelo.setRowCount(0);

        // 5. Recorrer todos los registros e insertarlos en la tabla
        while (rs.next()) {
            Object[] fila = new Object[7];
            fila[0] = rs.getInt("id_pedido");
            fila[1] = rs.getString("id_usuario");
            fila[2] = rs.getString("fecha_hora");
            fila[3] = rs.getDouble("subtotal");
            fila[4] = rs.getDouble("descuento");
            fila[5] = rs.getDouble("total");
            fila[6] = rs.getString("estado");

            modelo.addRow(fila);
        }

        javax.swing.JOptionPane.showMessageDialog(null, "Tabla actualizada correctamente.");

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al actualizar la tabla: " + e.getMessage());
    }
    
    }//GEN-LAST:event_botonCafe1ActionPerformed

    private void botonAmarillo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo1ActionPerformed
                                            
    try {
        // 1. Limpiar todos los campos de texto
        if (txtId != null) txtId.setText("");
        if (txtFecha != null) txtFecha.setText("");
        if (txtSubtotal != null) txtSubtotal.setText("");
        if (txtDescuento != null) txtDescuento.setText("");
        if (txtTotal != null) txtTotal.setText("");
        if (txtUsuario != null) txtUsuario.setText("");
        if (txtEstado != null) txtEstado.setText("");

        // 2. Deseleccionar cualquier fila de la tabla
        if (jTable1 != null) {
            jTable1.clearSelection();
        }

        // 3. Devolver el foco/cursor a la casilla de ID
        if (txtId != null) {
            txtId.requestFocus();
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al limpiar el formulario: " + e.getMessage());
    }

    }//GEN-LAST:event_botonAmarillo1ActionPerformed
public void cargarTablaPedidos() {
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel();
        modelo.addColumn("ID Pedido");
        modelo.addColumn("Usuario");
        modelo.addColumn("Fecha/Hora");
        modelo.addColumn("Subtotal");
        modelo.addColumn("Descuento");
        modelo.addColumn("Total");
        modelo.addColumn("Estado");

        jTable1.setModel(modelo);

        try {
            Modelo.Conexion cn = new Modelo.Conexion();
            java.sql.Connection con = cn.conectar();

            String sql = "SELECT id_pedido, id_usuario, fecha_hora, subtotal, descuento, total, estado FROM pedidos";
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id_pedido");
                fila[1] = rs.getInt("id_usuario");
                fila[2] = rs.getString("fecha_hora");
                fila[3] = rs.getDouble("subtotal");
                fila[4] = rs.getDouble("descuento");
                fila[5] = rs.getDouble("total");
                fila[6] = rs.getString("estado");

                modelo.addRow(fila);
            }
            // Desactiva el editor para todas las celdas de la tabla
jTable1.setDefaultEditor(Object.class, null);

            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al cargar la tabla: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void boton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton4ActionPerformed
      String idTexto = txtId.getText().trim();

    // 1. Validar que la casilla no esté vacía
    if (idTexto.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Por favor, ingrese o seleccione el ID del pedido a borrar.");
        return;
    }

    // 2. Pedir confirmación antes de eliminar
    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
        null, 
        "¿Está seguro de que desea eliminar el pedido ID " + idTexto + "?", 
        "Confirmar eliminación", 
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirmacion != javax.swing.JOptionPane.YES_OPTION) {
        return; // Si presiona "No", se cancela la eliminación
    }

    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();

    // 3. Consulta SQL corregida con la tabla 'pedidos' y 'id_pedido'
    String sql = "DELETE FROM pedidos WHERE id_pedido = ?";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(idTexto));

        int res = pst.executeUpdate();
        if (res > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "¡Pedido eliminado exitosamente!");

            // 4. Limpiar las casillas de texto
            txtId.setText("");
            txtFecha.setText("");
            txtSubtotal.setText("");
            txtDescuento.setText("");
            txtTotal.setText("");
            if (txtUsuario != null) txtUsuario.setText("");
            if (txtEstado != null) txtEstado.setText("");

            // 5. Limpiar la tabla en pantalla
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);

        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No se encontró ningún pedido con ese ID.");
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al borrar: " + e.getMessage());
    }
    }//GEN-LAST:event_boton4ActionPerformed

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    public void cargarTabla1Pedidos() {
     // 1. Obtener la fila seleccionada de la tabla
    int fila = jTable1.getSelectedRow();
    
    if (fila == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Por favor, selecciona un pedido de la tabla.");
        return;
    }

    // 2. Obtener el ID del pedido de la fila seleccionada (Columna 0)
    int idPedido = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
    
    // 3. Obtener el nuevo estado escrito en el txtEstado
    String nuevoEstado = txtEstado.getText().trim();

    if (nuevoEstado.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Ingresa un estado válido (ej. ENTREGADO).");
        return;
    }

    // 4. Actualizar en MySQL
    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();
    String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, nuevoEstado.toUpperCase()); // Asegura enviar 'ENTREGADO' en mayúsculas
        pst.setInt(2, idPedido);

        int resultado = pst.executeUpdate();

        if (resultado > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "¡Estado actualizado correctamente!");
            
            // 5. RECARGAR LA TABLA PARA VER EL CAMBIO
            cargarTabla1Pedidos();
        }
        con.close();
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al actualizar estado: " + e.getMessage());
    }
    }
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cabecera;
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
    private Componentes.Tabla jTable1;
    private Labels.LabelEscalable labelEscalable10;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    private Labels.LabelEscalable labelEscalable5;
    private Labels.LabelEscalable labelEscalable6;
    private Labels.LabelEscalable labelEscalable7;
    private Labels.LabelEscalable labelEscalable8;
    private Labels.LabelEscalable labelEscalable9;
    private Componentes.TexboxtUsuarios txtDescuento;
    private Componentes.TexboxtUsuarios txtEstado;
    private Componentes.TexboxtUsuarios txtFecha;
    private Componentes.TexboxtUsuarios txtId;
    private Componentes.TexboxtUsuarios txtSubtotal;
    private Componentes.TexboxtUsuarios txtTotal;
    private Componentes.TexboxtUsuarios txtUsuario;
    // End of variables declaration//GEN-END:variables
}
