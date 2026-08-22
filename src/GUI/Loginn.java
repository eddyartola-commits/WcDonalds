package GUI;


public class Loginn extends javax.swing.JFrame {

    public Loginn() {
        initComponents();
        this.setExtendedState(Loginn.MAXIMIZED_BOTH);
        
        
        

    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonAzul1 = new Componentes.BotonAzul();
        botonAzul2 = new Componentes.BotonAzul();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelEscalable3 = new Labels.LabelEscalable();
        botonAzul3 = new Componentes.BotonAzul();
        labelEscalable2 = new Labels.LabelEscalable();
        botonRojo1 = new Componentes.BotonRojo();
        labelEscalable4 = new Labels.LabelEscalable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Speedee", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("WcDonal´s");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 280, 100));

        jLabel2.setFont(new java.awt.Font("Speedee", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Bienvenido a ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 370, -1));

        labelEscalable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Gemini_Generated_Image_nev4cjnev4cjnev4 (1).png"))); // NOI18N
        labelEscalable3.setText("labelEscalable3");
        jPanel1.add(labelEscalable3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, -30, 620, 440));

        botonAzul3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAzul3ActionPerformed(evt);
            }
        });
        jPanel1.add(botonAzul3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 520, 330, 80));
        jPanel1.add(labelEscalable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 730, -1, -1));
        jPanel1.add(botonRojo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 500, 330, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, 900, 1431));

        labelEscalable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        labelEscalable4.setText("labelEscalable1");
        getContentPane().add(labelEscalable4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 1340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAzul3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAzul3ActionPerformed

        Administrador nuevo = new Administrador();
        nuevo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonAzul3ActionPerformed

   
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
            java.util.logging.Logger.getLogger(Loginn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loginn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loginn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loginn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loginn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Componentes.BotonAzul botonAzul1;
    private Componentes.BotonAzul botonAzul2;
    private Componentes.BotonAzul botonAzul3;
    private Componentes.BotonRojo botonRojo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private Labels.LabelEscalable labelEscalable2;
    private Labels.LabelEscalable labelEscalable3;
    private Labels.LabelEscalable labelEscalable4;
    // End of variables declaration//GEN-END:variables
}
