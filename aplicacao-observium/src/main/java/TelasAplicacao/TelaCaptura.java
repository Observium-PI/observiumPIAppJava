/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TelasAplicacao;

import Componentes.App;
import java.awt.Color;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabio.gdesouza
 */
public class TelaCaptura extends javax.swing.JFrame {

    /**
     * Creates new form TelaCaptura
     */
    public TelaCaptura() {
        initComponents();
        setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bttIniciarApp = new javax.swing.JButton();
        bttRetornar = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        labelTexto = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bttIniciarApp.setBackground(new java.awt.Color(0, 0, 0));
        bttIniciarApp.setForeground(new java.awt.Color(255, 255, 255));
        bttIniciarApp.setText("Iniciar");
        bttIniciarApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttIniciarAppActionPerformed(evt);
            }
        });
        getContentPane().add(bttIniciarApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 250, 40));

        bttRetornar.setBackground(new java.awt.Color(0, 0, 0));
        bttRetornar.setForeground(new java.awt.Color(255, 255, 255));
        bttRetornar.setText("Sair");
        bttRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttRetornarActionPerformed(evt);
            }
        });
        getContentPane().add(bttRetornar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, 150, 40));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo_Observium_Branco.png"))); // NOI18N
        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        labelTexto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelTexto.setForeground(new java.awt.Color(255, 255, 255));
        labelTexto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTexto.setText("Captura de dados.");
        getContentPane().add(labelTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 610, 70));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FundoObservium2.jpg"))); // NOI18N
        background.setText("jLabel1");
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttRetornarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_bttRetornarActionPerformed

    private void bttIniciarAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttIniciarAppActionPerformed
        App aplicacao = new App();
        try {
            aplicacao.aplicacao();
            labelTexto.setForeground(Color.green);
            labelTexto.setText("Dados capturados com sucesso!");
        } catch (java.lang.NumberFormatException ex) {
            labelTexto.setForeground(Color.red);
            labelTexto.setText("Máquina não cadastrada.");
        } catch (UnknownHostException ex) {
            Logger.getLogger(TelaCaptura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(TelaCaptura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bttIniciarAppActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCaptura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCaptura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCaptura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCaptura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCaptura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton bttIniciarApp;
    private javax.swing.JButton bttRetornar;
    private javax.swing.JLabel labelTexto;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
