package MenuAdministrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Conexion.ConexionMySQL;

public class Usuarios extends javax.swing.JPanel {

    public Usuarios() {
        initComponents();

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);

        probarConexionTabla();

    }

    public void probarConexionTabla() {
        // 1. Obtener el modelo de la tabla
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabla1.getModel();
        model.setRowCount(0); // Limpiar filas de prueba anteriores

        // 2. Traer los datos de MySQL
        Conexion.ProductoDAO dao = new Conexion.ProductoDAO();
        java.util.List<Object[]> usuarios = dao.obtenerUsuariosParaTabla();

        // 3. Insertar fila por fila en la tabla
        for (Object[] fila : usuarios) {
            model.addRow(fila);
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
        Nombre = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelEscalable3 = new Labels.LabelEscalable();
        texboxtUsuarios1 = new Componentes.TexboxtUsuarios();
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

        Cabecera.setOpaque(false);
        Cabecera.setPreferredSize(new java.awt.Dimension(100, 150));
        Cabecera.setLayout(new java.awt.GridBagLayout());

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (13)_1.png"))); // NOI18N
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

        PanelCorreo.setBackground(new java.awt.Color(255, 204, 204));
        PanelCorreo.setOpaque(false);
        PanelCorreo.setPreferredSize(new java.awt.Dimension(150, 110));
        PanelCorreo.setLayout(new java.awt.BorderLayout());

        Nombre3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre3.setText("                  Correo");
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
        texboxtUsuarios4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texboxtUsuarios4ActionPerformed(evt);
            }
        });
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
        Nombre2.setText("                  Clave");
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
        texboxtUsuarios3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texboxtUsuarios3ActionPerformed(evt);
            }
        });
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
        Nombre1.setText("                  Usuario");
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
        texboxtUsuarios2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texboxtUsuarios2ActionPerformed(evt);
            }
        });
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

        Nombre.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        Nombre.setText("                  Nombre");
        PanelNombre.add(Nombre, java.awt.BorderLayout.NORTH);

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
        texboxtUsuarios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texboxtUsuarios1ActionPerformed(evt);
            }
        });
        jPanel5.add(texboxtUsuarios1, java.awt.BorderLayout.CENTER);
        texboxtUsuarios1.getAccessibleContext().setAccessibleName("");

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
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 6;
        PanelOpciones.add(botonCafe1, gridBagConstraints);

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
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints.gridy = 4;
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
        jScrollPane1.setViewportView(tabla1);

        ContedorTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(ContedorTabla, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botonAmarillo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo2ActionPerformed
        String usuario = texboxtUsuarios2.getText().trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el usuario que deseas buscar.");
            return;
        }

        String sql = "SELECT nombre, usuario, clave, correo FROM usuarios WHERE usuario = ?";

        try {
            Connection con = ConexionMySQL.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);

            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                texboxtUsuarios1.setText(rs.getString("nombre"));
                texboxtUsuarios2.setText(rs.getString("usuario"));
                texboxtUsuarios3.setText(rs.getString("clave"));
                texboxtUsuarios4.setText(rs.getString("correo"));

                JOptionPane.showMessageDialog(this, "Usuario encontrado.");

            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al buscar usuario: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_botonAmarillo2ActionPerformed

    private void texboxtUsuarios3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texboxtUsuarios3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texboxtUsuarios3ActionPerformed

    private void texboxtUsuarios4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texboxtUsuarios4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texboxtUsuarios4ActionPerformed

    private void texboxtUsuarios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texboxtUsuarios1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texboxtUsuarios1ActionPerformed

    private void botonVerdeUsuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerdeUsuario1ActionPerformed
        String nombre = texboxtUsuarios1.getText().trim();
        String usuario = texboxtUsuarios2.getText().trim();
        String clave = texboxtUsuarios3.getText().trim();
        String correo = texboxtUsuarios4.getText().trim();

        if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        String sql = "INSERT INTO usuarios (nombre, usuario, clave, id_rol, correo) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = ConexionMySQL.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, clave);

            // Por el momento lo registra como Cajero (id_rol = 2)
            ps.setInt(4, 2);

            ps.setString(5, correo);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");

            texboxtUsuarios1.setText("");
            texboxtUsuarios2.setText("");
            texboxtUsuarios3.setText("");
            texboxtUsuarios4.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al crear usuario: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_botonVerdeUsuario1ActionPerformed

    private void botonCafe1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCafe1ActionPerformed
        String nombre = texboxtUsuarios1.getText().trim();
        String usuario = texboxtUsuarios2.getText().trim();
        String clave = texboxtUsuarios3.getText().trim();
        String correo = texboxtUsuarios4.getText().trim();

        if (nombre.isEmpty() || usuario.isEmpty()
                || clave.isEmpty() || correo.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        String sql = "UPDATE usuarios "
                + "SET nombre = ?, clave = ?, correo = ? "
                + "WHERE usuario = ?";

        try {
            Connection con = ConexionMySQL.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, clave);
            ps.setString(3, correo);
            ps.setString(4, usuario);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuario actualizado correctamente."
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el usuario."
                );
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_botonCafe1ActionPerformed

    private void boton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton4ActionPerformed
        String usuario = texboxtUsuarios2.getText().trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero busca o ingresa un usuario."
            );
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas eliminar al usuario " + usuario + "?",
                "Eliminar usuario",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM usuarios WHERE usuario = ?";

        try {
            Connection con = ConexionMySQL.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);

            int filas = ps.executeUpdate();

            if (filas > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario eliminado correctamente."
                );

                // Limpiar campos
                texboxtUsuarios1.setText("");
                texboxtUsuarios2.setText("");
                texboxtUsuarios3.setText("");
                texboxtUsuarios4.setText("");

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el usuario."
                );
            }

            ps.close();
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_boton4ActionPerformed

    private void botonAmarillo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAmarillo1ActionPerformed
        texboxtUsuarios1.setText("");
        texboxtUsuarios2.setText("");
        texboxtUsuarios3.setText("");
        texboxtUsuarios4.setText("");

        // Regresa el cursor al campo Nombre
        texboxtUsuarios1.requestFocus();
    }//GEN-LAST:event_botonAmarillo1ActionPerformed

    private void texboxtUsuarios2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texboxtUsuarios2ActionPerformed

    }//GEN-LAST:event_texboxtUsuarios2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cabecera;
    private Componentes.PanelRedondeadoSombra CabeceraCentral;
    private Componentes.PanelRedondeadoSombra ContedorTabla;
    private javax.swing.JPanel ContenedorBuscador;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Nombre1;
    private javax.swing.JLabel Nombre2;
    private javax.swing.JLabel Nombre3;
    private javax.swing.JPanel PanelClave;
    private Componentes.PanelRedondeadoSombra PanelContenedor;
    private javax.swing.JPanel PanelCorreo;
    private javax.swing.JPanel PanelNombre;
    private javax.swing.JPanel PanelOpciones;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    private Labels.LabelEscalable labelEscalable5;
    private Labels.LabelEscalable labelEscalable6;
    private Labels.LabelEscalable labelEscalable7;
    private Componentes.Tabla tabla1;
    private Componentes.TexboxtUsuarios texboxtUsuarios1;
    private Componentes.TexboxtUsuarios texboxtUsuarios2;
    private Componentes.TexboxtUsuarios texboxtUsuarios3;
    private Componentes.TexboxtUsuarios texboxtUsuarios4;
    // End of variables declaration//GEN-END:variables
}
