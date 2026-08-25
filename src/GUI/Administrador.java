
package GUI;

public class Administrador extends javax.swing.JFrame {


    public Administrador() {
        initComponents();
        this.setExtendedState(Administrador.MAXIMIZED_BOTH);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelEscalable2 = new Labels.LabelEscalable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelEscalable5 = new Labels.LabelEscalable();
        labelEscalable3 = new Labels.LabelEscalable();
        textbox1 = new Componentes.textbox();
        textbox2 = new Componentes.textbox();
        boton1 = new Componentes.boton();
        lineaGris1 = new Componentes.LineaGris();
        labelEscalable1 = new Labels.LabelEscalable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        labelEscalable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1000040230.png"))); // NOI18N
        labelEscalable2.setText("labelEscalable2");
        jPanel1.add(labelEscalable2);
        labelEscalable2.setBounds(-10, 0, 690, 1050);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Contraseña/Pin ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(780, 530, 550, 70);

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Acceso con privilegios completos");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(1070, 250, 690, 70);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Usuario/N° de ID");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(780, 350, 550, 70);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ADMINISTRADOR");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(1150, 180, 550, 70);

        labelEscalable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ChatGPT Image 25 ago 2026, 10_55_39.png"))); // NOI18N
        labelEscalable5.setText("labelEscalable1");
        jPanel1.add(labelEscalable5);
        labelEscalable5.setBounds(790, 630, 80, 70);

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Agregar un título.png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        jPanel1.add(labelEscalable3);
        labelEscalable3.setBounds(1190, 0, 320, 190);
        jPanel1.add(textbox1);
        textbox1.setBounds(780, 620, 1060, 90);
        jPanel1.add(textbox2);
        textbox2.setBounds(780, 420, 1060, 90);
        jPanel1.add(boton1);
        boton1.setBounds(780, 760, 1090, 80);
        jPanel1.add(lineaGris1);
        lineaGris1.setBounds(780, 860, 1090, 30);

        labelEscalable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ChatGPT Image 25 ago 2026, 10_55_39.png"))); // NOI18N
        labelEscalable1.setText("labelEscalable1");
        jPanel1.add(labelEscalable1);
        labelEscalable1.setBounds(790, 640, 80, 70);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 0, 1960, 1020);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  
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
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Administrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.boton boton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private Labels.LabelEscalable labelEscalable1;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable5;
    private Componentes.LineaGris lineaGris1;
    private Componentes.textbox textbox1;
    private Componentes.textbox textbox2;
    // End of variables declaration//GEN-END:variables
}
