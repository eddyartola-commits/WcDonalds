
package GUI;


public class Login1 extends javax.swing.JFrame {


    public Login1() {
        initComponents();
        this.setExtendedState(Login1.MAXIMIZED_BOTH);
        
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        labelEscalable1 = new Labels.LabelEscalable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        botonVerde1 = new Componentes.BotonVerde();
        labelEscalable3 = new Labels.LabelEscalable();
        labelEscalable6 = new Labels.LabelEscalable();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        botonRojo1 = new Componentes.BotonRojo();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelEscalable5 = new Labels.LabelEscalable();
        labelEscalable2 = new Labels.LabelEscalable();
        labelEscalable4 = new Labels.LabelEscalable();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 255, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(870, 810));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        labelEscalable1.setMaximumSize(new java.awt.Dimension(800, 5000));
        labelEscalable1.setMinimumSize(new java.awt.Dimension(800, 800));
        labelEscalable1.setPreferredSize(new java.awt.Dimension(800, 2500));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(labelEscalable1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(880, 900));
        jPanel3.setLayout(null);

        botonVerde1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerde1ActionPerformed(evt);
            }
        });
        jPanel3.add(botonVerde1);
        botonVerde1.setBounds(98, 710, 320, 90);

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc_1.png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        jPanel3.add(labelEscalable3);
        labelEscalable3.setBounds(320, 10, 230, 170);

        labelEscalable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (2).png"))); // NOI18N
        jPanel3.add(labelEscalable6);
        labelEscalable6.setBounds(175, 408, 160, 150);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("pedidos y operaciones.");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(600, 640, 250, 40);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("configuraciones y mas.");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(160, 640, 250, 40);

        botonRojo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRojo1ActionPerformed(evt);
            }
        });
        jPanel3.add(botonRojo1);
        botonRojo1.setBounds(525, 710, 320, 90);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(39, 116, 45));
        jLabel1.setText("Administrador");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(142, 550, 250, 80);

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Bienvenido a");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(200, 200, 270, 80);

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("Cajero");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(637, 550, 120, 80);

        labelEscalable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (1).png"))); // NOI18N
        jPanel3.add(labelEscalable5);
        labelEscalable5.setBounds(610, 390, 170, 160);

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (4).png"))); // NOI18N
        jPanel3.add(labelEscalable2);
        labelEscalable2.setBounds(60, 360, 390, 470);

        labelEscalable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Componentes/Wc (4).png"))); // NOI18N
        jPanel3.add(labelEscalable4);
        labelEscalable4.setBounds(490, 360, 390, 470);

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("WcDonald's");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(470, 200, 270, 80);

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Seleccione una opcion para acceder al sistema  ");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(250, 280, 390, 40);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Accede al sistema de ventas,");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(570, 610, 250, 40);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Gestiona usuarios, reportes,");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(140, 610, 250, 40);

        jPanel2.add(jPanel3, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVerde1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerde1ActionPerformed

        Administrador1 nuevo = new Administrador1();
        nuevo.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botonVerde1ActionPerformed

    private void botonRojo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRojo1ActionPerformed

        Cajero1 nuevo = new Cajero1();
        nuevo.setVisible(true);
        this.dispose();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRojo1ActionPerformed

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
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.BotonRojo botonRojo1;
    private Componentes.BotonVerde botonVerde1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private Labels.LabelEscalable labelEscalable1;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    private Labels.LabelEscalable labelEscalable5;
    private Labels.LabelEscalable labelEscalable6;
    // End of variables declaration//GEN-END:variables
}
