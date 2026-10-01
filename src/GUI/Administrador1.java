
package GUI;

import Conexion.ConexionMySQL;
import MenuAdministrador.Admi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Administrador1 extends javax.swing.JFrame {

    
    
 public Administrador1() {
           aplicarFondoPantalla();
         initComponents();
             hacerPanelesTransparentes();
             this.setExtendedState(Administrador1.MAXIMIZED_BOTH);

    }
 
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        labelEscalable2 = new Labels.LabelEscalable();
        jPanel2 = new javax.swing.JPanel();
        panelRedondeadoSombra1 = new Componentes.PanelRedondeadoSombra();
        jLabel1 = new javax.swing.JLabel();
        labelEscalable1 = new Labels.LabelEscalable();
        txtNombre = new Componentes.textbox();
        txtContrasena = new Componentes.textbox();
        btnIniciarSesion = new Componentes.boton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new Componentes.textbox();
        jPanel3 = new javax.swing.JPanel();
        botonVolver1 = new Componentes.BotonVolver();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(650, 200));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (8).png"))); // NOI18N
        labelEscalable2.setMaximumSize(new java.awt.Dimension(600, 600));
        labelEscalable2.setMinimumSize(new java.awt.Dimension(600, 600));
        labelEscalable2.setPreferredSize(new java.awt.Dimension(500, 500));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 40, 0);
        jPanel1.add(labelEscalable2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        panelRedondeadoSombra1.setBackground(new java.awt.Color(252, 241, 223));
        panelRedondeadoSombra1.setPreferredSize(new java.awt.Dimension(570, 600));
        panelRedondeadoSombra1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ADMINISTRADOR");
        panelRedondeadoSombra1.add(jLabel1);
        jLabel1.setBounds(177, 142, 210, 40);

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Agregar un título.png"))); // NOI18N
        panelRedondeadoSombra1.add(labelEscalable1);
        labelEscalable1.setBounds(169, 6, 210, 130);
        panelRedondeadoSombra1.add(txtNombre);
        txtNombre.setBounds(75, 212, 420, 60);
        panelRedondeadoSombra1.add(txtContrasena);
        txtContrasena.setBounds(75, 432, 420, 60);

        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });
        panelRedondeadoSombra1.add(btnIniciarSesion);
        btnIniciarSesion.setBounds(70, 510, 440, 70);

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");
        panelRedondeadoSombra1.add(jLabel4);
        jLabel4.setBounds(75, 172, 210, 40);

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Usuario");
        panelRedondeadoSombra1.add(jLabel3);
        jLabel3.setBounds(75, 282, 210, 40);

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contraseña");
        panelRedondeadoSombra1.add(jLabel5);
        jLabel5.setBounds(75, 392, 210, 40);
        panelRedondeadoSombra1.add(txtUsuario);
        txtUsuario.setBounds(75, 322, 420, 60);

        jPanel2.add(panelRedondeadoSombra1, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(20, 627));
        jPanel3.setMinimumSize(new java.awt.Dimension(20, 627));
        jPanel3.setPreferredSize(new java.awt.Dimension(60, 100));

        botonVolver1.setText("botonVolver1");
        botonVolver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolver1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1188, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(533, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolver1ActionPerformed
        Login1 nuevo = new Login1();
        nuevo.setVisible(true);
        nuevo.setLocationRelativeTo(null);

    this.dispose();
    }//GEN-LAST:event_botonVolver1ActionPerformed

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed

        String nombre = txtNombre.getTexto().trim();
        String usuario = txtUsuario.getTexto().trim();
        String contrasena = txtContrasena.getTexto().trim();

        // Verificar campos vacíos
        if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos."
            );

            return;
        }

        // Consulta para buscar al usuario y obtener su rol
        String sql
                = "SELECT u.nombre, r.nombre AS rol "
                + "FROM usuarios u "
                + "INNER JOIN roles r ON u.id_rol = r.id_rol "
                + "WHERE u.nombre = ? "
                + "AND u.usuario = ? "
                + "AND u.clave = ?";

        try {

            // Conectar con MySQL
            Connection con = ConexionMySQL.conectar();

            if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo conectar con la base de datos."
                );

                return;
            }

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, usuario);
            ps.setString(3, contrasena);

            ResultSet rs = ps.executeQuery();

            // Si encontró al usuario
             if (rs.next()) {

            String rol = rs.getString("rol");

            if (rol.equalsIgnoreCase("Administrador")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " + nombre
                );

                // SOLO AQUÍ ABRE ADMIN
                Admi ventanaAdmin = new Admi();
                ventanaAdmin.setVisible(true);
                ventanaAdmin.setLocationRelativeTo(null);

                // Cierra el login
                this.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Este usuario no tiene permisos de Administrador."
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
    

    }//GEN-LAST:event_btnIniciarSesionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.BotonVolver botonVolver1;
    private Componentes.boton btnIniciarSesion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private Labels.LabelEscalable labelEscalable1;
    private Labels.LabelEscalable labelEscalable2;
    private Componentes.PanelRedondeadoSombra panelRedondeadoSombra1;
    private Componentes.textbox txtContrasena;
    private Componentes.textbox txtNombre;
    private Componentes.textbox txtUsuario;
    // End of variables declaration//GEN-END:variables
}
