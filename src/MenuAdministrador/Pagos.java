
package MenuAdministrador;

import javax.swing.table.DefaultTableModel;
import Conexion.*;

public class Pagos extends javax.swing.JPanel {
    public Pagos() {
        initComponents();
     
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);
        
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        
        probarConexionTabla();
        
    }
    
    public void cargarTablaPagos() {
    // Definir el modelo de la tabla para bloquear la edición directa
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(
        new String[]{"ID Pago", "ID Pedido", "Método", "Total Pagado", "Efectivo Recibido", "Cambio", "Fecha"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Reemplaza 'jTable1' por el Variable Name real de tu JTable si es diferente
    jTablePagos.setModel(modelo);

    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();
    String sql = "SELECT * FROM pagos ORDER BY id_pago DESC";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[7];
            fila[0] = rs.getInt("id_pago");
            fila[1] = rs.getInt("id_pedido");
            fila[2] = rs.getString("metodo");
            fila[3] = rs.getDouble("total_pagado");
            fila[4] = rs.getDouble("efectivo_recibido");
            fila[5] = rs.getDouble("cambio");
            fila[6] = rs.getString("fecha");

            modelo.addRow(fila);
        }

        pst.close();
        con.close();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al cargar la tabla de pagos: " + e.getMessage());
    }
}
public void probarConexionTabla() {
 String[] colsPedidos = {"", "ID Pago", "ID Pedido", "Metodo", "Total Pagado", "Efectivo Recibido", "Cambio", "Fecha", "Acciones"};
    int[] meanchosPedidos = {40, 70, 120, 160, 100, 100, 100, 120, 110};

    // Configura las columnas dinámicamente
    jTablePagos.configurarColumnas(colsPedidos, meanchosPedidos);

    // Carga las filas devueltas por el DAO
    DefaultTableModel modelo = (DefaultTableModel) jTablePagos.getModel();
    modelo.setRowCount(0);

    // Llama al nuevo método de PagoDAO
    Conexion.PagoDAO pagoDAO = new Conexion.PagoDAO();
    for (Object[] fila : pagoDAO.obtenerPagosParaTabla()) {
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
        texboxtUsuarios7 = new Componentes.TexboxtUsuarios();
        PanelTotal = new javax.swing.JPanel();
        Nombre5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        labelEscalable9 = new Labels.LabelEscalable();
        txtCambio = new Componentes.TexboxtUsuarios();
        PanelDescuento = new javax.swing.JPanel();
        Nombre4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        labelEscalable8 = new Labels.LabelEscalable();
        txtEfectivo = new Componentes.TexboxtUsuarios();
        PanelCorreo = new javax.swing.JPanel();
        Nombre3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        labelEscalable6 = new Labels.LabelEscalable();
        txtTotalPagado = new Componentes.TexboxtUsuarios();
        PanelClave = new javax.swing.JPanel();
        Nombre2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        labelEscalable5 = new Labels.LabelEscalable();
        txtMetodo = new Componentes.TexboxtUsuarios();
        PanelUsuario = new javax.swing.JPanel();
        Nombre1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelEscalable4 = new Labels.LabelEscalable();
        txtIdPedido = new Componentes.TexboxtUsuarios();
        PanelNombre = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelEscalable3 = new Labels.LabelEscalable();
        txtIdPago = new Componentes.TexboxtUsuarios();
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
        jTablePagos = new Componentes.Tabla();

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

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pagos.png"))); // NOI18N
        labelEscalable2.setPreferredSize(new java.awt.Dimension(500, 150));
        Cabecera.add(labelEscalable2, new java.awt.GridBagConstraints());

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
        Nombre5.setForeground(new java.awt.Color(0, 0, 0));
        Nombre5.setText("                  Cambio");
        PanelTotal.add(Nombre5, java.awt.BorderLayout.NORTH);

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel11.setOpaque(false);
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel11.setLayout(new java.awt.BorderLayout());

        labelEscalable9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable9.setText("labelEscalable3");
        labelEscalable9.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel11.add(labelEscalable9, java.awt.BorderLayout.WEST);

        txtCambio.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtCambio.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel11.add(txtCambio, java.awt.BorderLayout.CENTER);

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
        Nombre4.setText("                  Efectivo");
        PanelDescuento.add(Nombre4, java.awt.BorderLayout.NORTH);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel10.setLayout(new java.awt.BorderLayout());

        labelEscalable8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable8.setText("labelEscalable3");
        labelEscalable8.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel10.add(labelEscalable8, java.awt.BorderLayout.WEST);

        txtEfectivo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtEfectivo.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel10.add(txtEfectivo, java.awt.BorderLayout.CENTER);

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
        Nombre3.setText("                  Total Pagado");
        PanelCorreo.add(Nombre3, java.awt.BorderLayout.NORTH);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel8.setLayout(new java.awt.BorderLayout());

        labelEscalable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable6.setText("labelEscalable3");
        labelEscalable6.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel8.add(labelEscalable6, java.awt.BorderLayout.WEST);

        txtTotalPagado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtTotalPagado.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel8.add(txtTotalPagado, java.awt.BorderLayout.CENTER);

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
        Nombre2.setText("                  Metodo");
        PanelClave.add(Nombre2, java.awt.BorderLayout.NORTH);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel7.setLayout(new java.awt.BorderLayout());

        labelEscalable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable5.setText("labelEscalable3");
        labelEscalable5.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel7.add(labelEscalable5, java.awt.BorderLayout.WEST);

        txtMetodo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtMetodo.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel7.add(txtMetodo, java.awt.BorderLayout.CENTER);

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
        Nombre1.setText("                  ID Pedido");
        PanelUsuario.add(Nombre1, java.awt.BorderLayout.NORTH);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel6.setLayout(new java.awt.BorderLayout());

        labelEscalable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable4.setText("labelEscalable3");
        labelEscalable4.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel6.add(labelEscalable4, java.awt.BorderLayout.WEST);

        txtIdPedido.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtIdPedido.setPreferredSize(new java.awt.Dimension(50, 52));
        jPanel6.add(txtIdPedido, java.awt.BorderLayout.CENTER);

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
        Nombre.setText("                  ID Pago");
        PanelNombre.add(Nombre, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel5.setLayout(new java.awt.BorderLayout());

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        labelEscalable3.setPreferredSize(new java.awt.Dimension(75, 16));
        jPanel5.add(labelEscalable3, java.awt.BorderLayout.WEST);

        txtIdPago.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        txtIdPago.setPreferredSize(new java.awt.Dimension(50, 52));
        txtIdPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPagoActionPerformed(evt);
            }
        });
        jPanel5.add(txtIdPago, java.awt.BorderLayout.CENTER);

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
        botonVerdeUsuario1.setText("CREAR USUARIO");
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

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(jTablePagos);

        ContedorTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(ContedorTabla, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botonVerdeUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerdeUsuario1ActionPerformed
        if (txtIdPedido.getText().trim().isEmpty() || 
    txtMetodo.getText().trim().isEmpty() || 
    txtTotalPagado.getText().trim().isEmpty() || 
    txtEfectivo.getText().trim().isEmpty()) {

    javax.swing.JOptionPane.showMessageDialog(null, "Por favor llena los campos: ID Pedido, Método, Total y Efectivo.");
    return;
}

try {
    int idPedido = Integer.parseInt(txtIdPedido.getText().trim());
    String metodo = txtMetodo.getText().trim();
    double totalPagado = Double.parseDouble(txtTotalPagado.getText().trim());
    double efectivo = Double.parseDouble(txtEfectivo.getText().trim());

    if (efectivo < totalPagado) {
        javax.swing.JOptionPane.showMessageDialog(null, "El efectivo es menor al total a pagar.");
        return;
    }

    double cambio = efectivo - totalPagado;
    txtCambio.setText(String.format("%.2f", cambio));

    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();

    // Insertar en pagos
    String sqlPago = "INSERT INTO pagos (id_pedido, metodo, total_pagado, efectivo_recibido, cambio, fecha) VALUES (?, ?, ?, ?, ?, NOW())";
    java.sql.PreparedStatement pstPago = con.prepareStatement(sqlPago);
    pstPago.setInt(1, idPedido);
    pstPago.setString(2, metodo);
    pstPago.setDouble(3, totalPagado);
    pstPago.setDouble(4, efectivo);
    pstPago.setDouble(5, cambio);

    int res = pstPago.executeUpdate();

    if (res > 0) {
        // Actualizar estado en pedidos a PAGADO
        String sqlPedido = "UPDATE pedidos SET estado = 'PAGADO' WHERE id_pedido = ?";
        java.sql.PreparedStatement pstPedido = con.prepareStatement(sqlPedido);
        pstPedido.setInt(1, idPedido);
        pstPedido.executeUpdate();
        pstPedido.close();

        javax.swing.JOptionPane.showMessageDialog(null, "¡Pago registrado exitosamente!");
        cargarTablaPagos();
        
        // Limpiar campos
        if (txtIdPago != null) txtIdPago.setText("");
        txtIdPedido.setText("");
        txtMetodo.setText("");
        txtTotalPagado.setText("");
        txtEfectivo.setText("");
        txtCambio.setText("");
    }

    pstPago.close();
    con.close();

} catch (NumberFormatException e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Error: Ingrese números válidos en ID, Total y Efectivo.");
} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Error en la Base de Datos: " + e.getMessage());
   
    }//GEN-LAST:event_botonVerdeUsuario1ActionPerformed
    }
    private void txtIdPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPagoActionPerformed

    private void botonCafe1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCafe1ActionPerformed
cargarTablaPagos(); // Refresca los datos en la tabla
if (txtIdPago != null) txtIdPago.setText("");
txtIdPedido.setText("");
txtMetodo.setText("");
txtTotalPagado.setText("");
txtEfectivo.setText("");
txtCambio.setText("");

javax.swing.JOptionPane.showMessageDialog(null, "Tabla de pagos actualizada.");
    }//GEN-LAST:event_botonCafe1ActionPerformed

    
    
    
 
    private void botonAmarillo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo2ActionPerformed
   String idBuscado = txtIdPago.getText().trim();

if (idBuscado.isEmpty()) {
    idBuscado = txtIdPedido.getText().trim();
}

if (idBuscado.isEmpty()) {
    javax.swing.JOptionPane.showMessageDialog(null, "Ingresa un ID de Pago o ID de Pedido para buscar.");
    return;
}

Modelo.Conexion cn = new Modelo.Conexion();
java.sql.Connection con = cn.conectar();
String sql = "SELECT * FROM pagos WHERE id_pago = ? OR id_pedido = ?";

try {
    java.sql.PreparedStatement pst = con.prepareStatement(sql);
    pst.setInt(1, Integer.parseInt(idBuscado));
    pst.setInt(2, Integer.parseInt(idBuscado));

    java.sql.ResultSet rs = pst.executeQuery();

    if (rs.next()) {
        txtIdPago.setText(String.valueOf(rs.getInt("id_pago")));
        txtIdPedido.setText(String.valueOf(rs.getInt("id_pedido")));
        txtMetodo.setText(rs.getString("metodo"));
        txtTotalPagado.setText(String.valueOf(rs.getDouble("total_pagado")));
        txtEfectivo.setText(String.valueOf(rs.getDouble("efectivo_recibido")));
        txtCambio.setText(String.valueOf(rs.getDouble("cambio")));
        
        javax.swing.JOptionPane.showMessageDialog(null, "¡Pago encontrado!");
    } else {
        javax.swing.JOptionPane.showMessageDialog(null, "No se encontró ningún pago con ese ID.");
    }

    pst.close();
    con.close();

} catch (NumberFormatException e) {
    javax.swing.JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
}
    }//GEN-LAST:event_botonAmarillo2ActionPerformed

    private void boton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton4ActionPerformed
      String idTexto = txtIdPago.getText().trim();

if (idTexto.isEmpty()) {
    javax.swing.JOptionPane.showMessageDialog(null, "Seleccione o busque primero un pago para borrar.");
    return;
}

int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
    null, 
    "¿Está seguro de eliminar el pago ID: " + idTexto + "?", 
    "Confirmar eliminación", 
    javax.swing.JOptionPane.YES_NO_OPTION
);

if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
    Modelo.Conexion cn = new Modelo.Conexion();
    java.sql.Connection con = cn.conectar();
    String sql = "DELETE FROM pagos WHERE id_pago = ?";

    try {
        java.sql.PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, Integer.parseInt(idTexto));

        int res = pst.executeUpdate();

        if (res > 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Pago eliminado correctamente.");
            cargarTablaPagos();
            
            txtIdPago.setText("");
            txtIdPedido.setText("");
            txtMetodo.setText("");
            txtTotalPagado.setText("");
            txtEfectivo.setText("");
            txtCambio.setText("");
        }

        pst.close();
        con.close();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al eliminar pago: " + e.getMessage());
    }
}
    }//GEN-LAST:event_boton4ActionPerformed

    private void botonAmarillo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo1ActionPerformed
     if (txtIdPago != null) txtIdPago.setText("");
txtIdPedido.setText("");
txtMetodo.setText("");
txtTotalPagado.setText("");
txtEfectivo.setText("");
txtCambio.setText("");

if (jTablePagos != null) {
    jTablePagos.clearSelection();
}

txtIdPedido.requestFocus();
    }//GEN-LAST:event_botonAmarillo1ActionPerformed


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
    private Componentes.Tabla jTablePagos;
    private Labels.LabelEscalable labelEscalable10;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    private Labels.LabelEscalable labelEscalable5;
    private Labels.LabelEscalable labelEscalable6;
    private Labels.LabelEscalable labelEscalable7;
    private Labels.LabelEscalable labelEscalable8;
    private Labels.LabelEscalable labelEscalable9;
    private Componentes.TexboxtUsuarios texboxtUsuarios7;
    private Componentes.TexboxtUsuarios txtCambio;
    private Componentes.TexboxtUsuarios txtEfectivo;
    private Componentes.TexboxtUsuarios txtIdPago;
    private Componentes.TexboxtUsuarios txtIdPedido;
    private Componentes.TexboxtUsuarios txtMetodo;
    private Componentes.TexboxtUsuarios txtTotalPagado;
    // End of variables declaration//GEN-END:variables
}
