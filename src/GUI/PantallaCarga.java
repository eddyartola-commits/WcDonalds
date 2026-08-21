
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class PantallaCarga extends javax.swing.JFrame {

  
    public PantallaCarga() {
        initComponents();
        this.setExtendedState(PantallaCarga.MAXIMIZED_BOTH);
        int tiempoEnMilisegundos = 4500;
        
        Timer timer = new Timer(tiempoEnMilisegundos, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Crear e instanciar la nueva ventana (ejemplo: Loginn)
                Loginn ventanaSiguiente = new Loginn();
                ventanaSiguiente.setVisible(true);

                // 2. Cerrar la pantalla de carga actual
                PantallaCarga.this.dispose();
            }
        });

        // IMPORTANTE: Decirle al Timer que solo se ejecute UNA VEZ y no en bucle
        timer.setRepeats(false); 

        // Iniciar el conteo
        timer.start();
        
        
 
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelEscalable1 = new Labels.LabelEscalable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Componentes/Wc.gif"))); // NOI18N
        getContentPane().add(labelEscalable1);
        labelEscalable1.setBounds(0, 0, 1490, 800);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(PantallaCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaCarga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaCarga().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Labels.LabelEscalable labelEscalable1;
    // End of variables declaration//GEN-END:variables
}
