
package GUI;
import Conexion.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Cajero1 extends javax.swing.JFrame {

  
    public Cajero1() {
        initComponents();
        this.setExtendedState(Cajero1.MAXIMIZED_BOTH);
        
        
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
        panelTarjetaForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelEscalable2 = new Labels.LabelEscalable();
        txtNombre = new Componentes.textbox();
        boton1 = new Componentes.boton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textUsuario = new Componentes.textbox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addContainerGap(1527, Short.MAX_VALUE))
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

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cajer.png"))); // NOI18N
        labelEscalable1.setMaximumSize(new java.awt.Dimension(600, 600));
        labelEscalable1.setMinimumSize(new java.awt.Dimension(600, 600));
        labelEscalable1.setPreferredSize(new java.awt.Dimension(600, 600));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(labelEscalable1, gridBagConstraints);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panelTarjetaForm.setBackground(new java.awt.Color(252, 241, 223));
        panelTarjetaForm.setPreferredSize(new java.awt.Dimension(500, 600));
        panelTarjetaForm.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ADMINISTRADOR");
        panelTarjetaForm.add(jLabel1);
        jLabel1.setBounds(160, 110, 210, 40);

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Agregar un título.png"))); // NOI18N
        panelTarjetaForm.add(labelEscalable2);
        labelEscalable2.setBounds(143, 0, 210, 130);
        panelTarjetaForm.add(txtNombre);
        txtNombre.setBounds(20, 190, 460, 80);

        boton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton1ActionPerformed(evt);
            }
        });
        panelTarjetaForm.add(boton1);
        boton1.setBounds(19, 420, 470, 80);

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre");
        panelTarjetaForm.add(jLabel4);
        jLabel4.setBounds(20, 150, 210, 40);

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 25)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Usuario");
        panelTarjetaForm.add(jLabel3);
        jLabel3.setBounds(20, 280, 210, 40);
        panelTarjetaForm.add(textUsuario);
        textUsuario.setBounds(20, 320, 460, 80);

        jPanel3.add(panelTarjetaForm, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolver1ActionPerformed

        Login1 nuevo = new Login1();
        nuevo.setVisible(true);
        this.dispose();
        

    }//GEN-LAST:event_botonVolver1ActionPerformed

    private void boton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton1ActionPerformed
  String nombre = txtNombre.getTexto().trim();
    String usuario = textUsuario.getTexto().trim();

    if (nombre.isEmpty() || usuario.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Complete todos los campos."
        );

        return;
    }

    String sql =
            "SELECT u.nombre, r.nombre AS rol "
            + "FROM usuarios u "
            + "INNER JOIN roles r ON u.id_rol = r.id_rol "
            + "WHERE u.nombre = ? "
            + "AND u.usuario = ?";

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

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String rol = rs.getString("rol");

            if (rol.equalsIgnoreCase("Cajero")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bienvenido " + nombre
                );

                // AQUÍ ABRIMOS EL MENÚ DEL CAJERO
                Cajero1 ventanaCajero = new Cajero1();
                ventanaCajero.setVisible(true);
                ventanaCajero.setLocationRelativeTo(null);

                this.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Este usuario no tiene permisos de Cajero."
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre o usuario incorrectos."
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
    }//GEN-LAST:event_boton1ActionPerformed
    }

   
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
    private Componentes.boton boton1;
    private Componentes.BotonVolver botonVolver1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private Labels.LabelEscalable labelEscalable1;
    private Labels.LabelEscalable labelEscalable2;
    private javax.swing.JPanel panelTarjetaForm;
    private Componentes.textbox textUsuario;
    private Componentes.textbox txtNombre;
    // End of variables declaration//GEN-END:variables
}
