
package GUI;
import Conexion.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Cajero1 extends javax.swing.JFrame {
          private boolean mostrarPassword = false;

  
    public Cajero1() {
        aplicarFondoPantalla();

        initComponents();
        hacerPanelesTransparentes();
        this.setExtendedState(Cajero1.MAXIMIZED_BOTH);
        textContraseña.setEsPassword(true);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        botonVolver1 = new Componentes.BotonVolver();
        jPanel2 = new javax.swing.JPanel();
        labelEscalable1 = new Labels.LabelEscalable();
        jPanel3 = new javax.swing.JPanel();
        panelRedondeadoSombra1 = new Componentes.PanelRedondeadoSombra();
        labelEscalable2 = new Labels.LabelEscalable();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new Componentes.textbox();
        jLabel3 = new javax.swing.JLabel();
        textContraseña = new Componentes.textbox();
        boton1 = new Componentes.boton();
        textUsuario1 = new Componentes.textbox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Icono = new Labels.LabelEscalable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/WCicono.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        botonVolver1.setText("botonVolver1");
        botonVolver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolver1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1218, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonVolver1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 0, 0));
        jPanel2.setInheritsPopupMenu(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(750, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 100));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cajero.png"))); // NOI18N
        labelEscalable1.setMaximumSize(new java.awt.Dimension(600, 600));
        labelEscalable1.setMinimumSize(new java.awt.Dimension(600, 600));
        labelEscalable1.setPreferredSize(new java.awt.Dimension(500, 500));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 0);
        jPanel2.add(labelEscalable1, gridBagConstraints);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panelRedondeadoSombra1.setBackground(new java.awt.Color(252, 241, 223));
        panelRedondeadoSombra1.setPreferredSize(new java.awt.Dimension(580, 650));
        panelRedondeadoSombra1.setLayout(null);

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (12).png"))); // NOI18N
        panelRedondeadoSombra1.add(labelEscalable2);
        labelEscalable2.setBounds(230, 20, 120, 110);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("CAJERO");
        panelRedondeadoSombra1.add(jLabel1);
        jLabel1.setBounds(230, 130, 120, 40);
        panelRedondeadoSombra1.add(txtNombre);
        txtNombre.setBounds(60, 210, 460, 65);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 23)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Contraseña");
        panelRedondeadoSombra1.add(jLabel3);
        jLabel3.setBounds(60, 410, 210, 40);
        panelRedondeadoSombra1.add(textContraseña);
        textContraseña.setBounds(50, 460, 460, 65);

        boton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton1ActionPerformed(evt);
            }
        });
        panelRedondeadoSombra1.add(boton1);
        boton1.setBounds(60, 548, 470, 70);
        panelRedondeadoSombra1.add(textUsuario1);
        textUsuario1.setBounds(60, 330, 460, 65);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 23)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Usuario");
        panelRedondeadoSombra1.add(jLabel5);
        jLabel5.setBounds(60, 290, 210, 40);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 23)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Nombre");
        panelRedondeadoSombra1.add(jLabel6);
        jLabel6.setBounds(60, 170, 210, 40);

        Icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo (1).png"))); // NOI18N
        Icono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoMouseClicked(evt);
            }
        });
        panelRedondeadoSombra1.add(Icono);
        Icono.setBounds(520, 470, 40, 50);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.7;
        jPanel3.add(panelRedondeadoSombra1, gridBagConstraints);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolver1ActionPerformed

        Login1 nuevo = new Login1();
        nuevo.setVisible(true);
        this.dispose();
        

    }//GEN-LAST:event_botonVolver1ActionPerformed

    private void hacerPanelesTransparentes() {
    // 1. Desactivar la opacidad de los paneles contenedores
    jPanel1.setOpaque(false);
    jPanel2.setOpaque(false);
    jPanel3.setOpaque(false);
    
    // 2. Si tienes contenedores internos dentro de jPanel2 o jPanel3, también hazlos transparentes
    // (Ejemplo: panelCentral.setOpaque(false);)
}
    
    private void aplicarFondoPantalla() {
    // Cargar la imagen desde la carpeta de tu proyecto
    java.io.File archivo = new java.io.File("src/Imagenes/fondoCajero.png"); // Asegúrate de colocar la ruta exacta de tu fondo
    
    if (archivo.exists()) {
        final javax.swing.ImageIcon icon = new javax.swing.ImageIcon(archivo.getAbsolutePath());
        
        // Asignar un panel personalizado como ContentPane del JFrame
        setContentPane(new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, 
                                   java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                // Dibuja la imagen estirándose a todo el ancho y alto de la ventana
                g2.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
            }
        });
        
        // Aplicar el BorderLayout original para no romper la posición de jPanel1, jPanel2 y jPanel3
        getContentPane().setLayout(new java.awt.BorderLayout());
    }
}
    private void boton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton1ActionPerformed
 
       String nombre = txtNombre.getTexto().trim();
    String usuario = textUsuario1.getTexto().trim();
    String clave = textContraseña.getTexto().trim();

    // 1. Validar que no haya campos vacíos
    if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Complete todos los campos."
        );
        return;
    }

    // 2. Consulta SQL verificando Nombre, Usuario y Contraseña (u.clave)
    String sql =
            "SELECT u.id_usuario, u.nombre, r.nombre AS rol "
            + "FROM usuarios u "
            + "INNER JOIN roles r ON u.id_rol = r.id_rol "
            + "WHERE u.nombre = ? "
            + "AND u.usuario = ? "
            + "AND u.clave = ?";

    try {
        Connection con = ConexionMySQL.conectar();

        if (con == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar con la base de datos."
            );
            return;
        }

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, usuario);
        ps.setString(3, clave);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String rol = rs.getString("rol");

            if (rol.equalsIgnoreCase("Cajero")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " + nombre
                );

int idUsuario = rs.getInt("id_usuario");

MenuCajero.Cajero ventanaCajero = new MenuCajero.Cajero(idUsuario);
ventanaCajero.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
ventanaCajero.setLocationRelativeTo(null);
ventanaCajero.setVisible(true);

                this.dispose(); // Cierra el Login de Cajero

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Este usuario no tiene permisos de Cajero."
                );
            }

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Nombre, usuario o contraseña incorrectos."
            );
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Error al iniciar sesión:\n" + e.getMessage()
        );
        e.printStackTrace();  
    }
    }//GEN-LAST:event_boton1ActionPerformed

    private void IconoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoMouseClicked
     if (mostrarPassword) {
        textContraseña.mostrarPassword(false); // Oculta la contraseña
        mostrarPassword = false;
    } else {
        textContraseña.mostrarPassword(true);  // Muestra la contraseña
        mostrarPassword = true;
    }
// TODO add your handling code here:
    }//GEN-LAST:event_IconoMouseClicked
    

   
    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(Cajero1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cajero1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cajero1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cajero1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cajero1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Labels.LabelEscalable Icono;
    private Componentes.boton boton1;
    private Componentes.BotonVolver botonVolver1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private Labels.LabelEscalable labelEscalable1;
    private Labels.LabelEscalable labelEscalable2;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra1;
    private Componentes.textbox textContraseña;
    private Componentes.textbox textUsuario1;
    private Componentes.textbox txtNombre;
    // End of variables declaration//GEN-END:variables
}
