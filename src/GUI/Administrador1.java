
package GUI;

import Conexion.ConexionMySQL;
import MenuAdministrador.Admi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Administrador1 extends javax.swing.JFrame {

 public Administrador1() {
        initComponents();
        this.setExtendedState(Administrador1.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelEscalable2 = new Labels.LabelEscalable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        botonVolver1 = new Componentes.BotonVolver();
        panelFormulario = new javax.swing.JPanel();
        panelTarjetaForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelEscalable1 = new Labels.LabelEscalable();
        txtNombre = new Componentes.textbox();
        txtContrasena = new Componentes.textbox();
        btnIniciarSesion = new Componentes.boton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new Componentes.textbox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(650, 200));
        jPanel1.setLayout(new java.awt.BorderLayout());

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1000040230.png"))); // NOI18N
        labelEscalable2.setText("labelEscalable2");
        jPanel1.add(labelEscalable2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

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
                .addComponent(botonVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(555, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(botonVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(534, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        panelFormulario.setBackground(new java.awt.Color(255, 255, 255));
        panelFormulario.setPreferredSize(new java.awt.Dimension(60, 80));
        panelFormulario.setLayout(new java.awt.GridBagLayout());

        panelTarjetaForm.setBackground(new java.awt.Color(255, 255, 255));
        panelTarjetaForm.setPreferredSize(new java.awt.Dimension(500, 600));
        panelTarjetaForm.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ADMINISTRADOR");
        panelTarjetaForm.add(jLabel1);
        jLabel1.setBounds(140, 110, 210, 40);

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Agregar un título.png"))); // NOI18N
        panelTarjetaForm.add(labelEscalable1);
        labelEscalable1.setBounds(143, 0, 210, 130);
        panelTarjetaForm.add(txtNombre);
        txtNombre.setBounds(40, 190, 420, 60);
        panelTarjetaForm.add(txtContrasena);
        txtContrasena.setBounds(40, 410, 420, 60);

        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });
        panelTarjetaForm.add(btnIniciarSesion);
        btnIniciarSesion.setBounds(30, 500, 440, 70);

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");
        panelTarjetaForm.add(jLabel4);
        jLabel4.setBounds(40, 150, 210, 40);

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Usuario");
        panelTarjetaForm.add(jLabel3);
        jLabel3.setBounds(40, 260, 210, 40);

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contraseña");
        panelTarjetaForm.add(jLabel5);
        jLabel5.setBounds(40, 370, 210, 40);
        panelTarjetaForm.add(txtUsuario);
        txtUsuario.setBounds(40, 300, 420, 60);

        panelFormulario.add(panelTarjetaForm, new java.awt.GridBagConstraints());

        jPanel2.add(panelFormulario, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

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
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelTarjetaForm;
    private Componentes.textbox txtContrasena;
    private Componentes.textbox txtNombre;
    private Componentes.textbox txtUsuario;
    // End of variables declaration//GEN-END:variables
}
