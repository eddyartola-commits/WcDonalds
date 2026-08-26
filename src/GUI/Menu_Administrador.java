
package GUI;

import java.awt.CardLayout;
import PanelesAdmi.Usuarios;
import PanelesAdmi.Productos;

public class Menu_Administrador extends javax.swing.JFrame {

private CardLayout cardLayout;
    
    public Menu_Administrador() {
        initComponents();
        this.setExtendedState(Menu_Administrador.MAXIMIZED_BOTH);
        
        cardLayout = (CardLayout) panelContenedor.getLayout();
        
        panelContenedor.add(new Usuarios(), "PANEL_USUARIOS");
         panelContenedor.add(new Productos(), "PANEL_PRODUCTOS");
        
        cardLayout.show(panelContenedor, "PANEL_USUARIOS");
        
        
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelEscalable1 = new Labels.LabelEscalable();
        botonAdmi1 = new Componentes.BotonAdmi();
        botonAdmi2 = new Componentes.BotonAdmi();
        botonAdmi3 = new Componentes.BotonAdmi();
        botonAdmi4 = new Componentes.BotonAdmi();
        botonAdmi5 = new Componentes.BotonAdmi();
        panelContenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Wc (6).png"))); // NOI18N
        jPanel1.add(labelEscalable1);
        labelEscalable1.setBounds(20, 30, 440, 120);

        botonAdmi1.setText("Productos");
        botonAdmi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdmi1ActionPerformed(evt);
            }
        });
        jPanel1.add(botonAdmi1);
        botonAdmi1.setBounds(710, 60, 220, 80);

        botonAdmi2.setText("Pagos");
        botonAdmi2.setActionCommand("Pagos ");
        botonAdmi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdmi2ActionPerformed(evt);
            }
        });
        jPanel1.add(botonAdmi2);
        botonAdmi2.setBounds(1370, 60, 220, 80);

        botonAdmi3.setText("Usuarios ");
        botonAdmi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdmi3ActionPerformed(evt);
            }
        });
        jPanel1.add(botonAdmi3);
        botonAdmi3.setBounds(490, 60, 220, 80);

        botonAdmi4.setText("Categorias");
        botonAdmi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdmi4ActionPerformed(evt);
            }
        });
        jPanel1.add(botonAdmi4);
        botonAdmi4.setBounds(930, 60, 220, 80);

        botonAdmi5.setText("Ventas");
        jPanel1.add(botonAdmi5);
        botonAdmi5.setBounds(1150, 60, 220, 80);

        panelContenedor.setLayout(new java.awt.CardLayout());
        jPanel1.add(panelContenedor);
        panelContenedor.setBounds(440, 160, 1470, 810);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1930, 1010);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAdmi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdmi3ActionPerformed

cardLayout.show(panelContenedor, "PANEL_USUARIOS");
// TODO add your handling code here:
    }//GEN-LAST:event_botonAdmi3ActionPerformed

    private void botonAdmi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdmi4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAdmi4ActionPerformed

    private void botonAdmi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdmi1ActionPerformed
        cardLayout.show(panelContenedor, "PANEL_PRODUCTOS");
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAdmi1ActionPerformed

    private void botonAdmi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdmi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonAdmi2ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_Administrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.BotonAdmi botonAdmi1;
    private Componentes.BotonAdmi botonAdmi2;
    private Componentes.BotonAdmi botonAdmi3;
    private Componentes.BotonAdmi botonAdmi4;
    private Componentes.BotonAdmi botonAdmi5;
    private javax.swing.JPanel jPanel1;
    private Labels.LabelEscalable labelEscalable1;
    private javax.swing.JPanel panelContenedor;
    // End of variables declaration//GEN-END:variables
}
