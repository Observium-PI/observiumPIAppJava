/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TelasAplicacao;

import Maquina.MaquinaCrud;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import com.github.britooo.looca.api.core.Looca;
import Componentes.Disk;
import Usuarios.UsuarioCrud;

/**
 *
 * @author User
 */
public class TelaHardware extends javax.swing.JFrame {
    
    BasicDataSource dataSource = new BasicDataSource();
    
    public String login;
    public String nome;
    
    /**
     * Creates new form TelaHardware
     */
    public TelaHardware() {
        initComponents();
        setLocationRelativeTo(this);
    }
    
    public TelaHardware(UsuarioCrud usuario) throws UnknownHostException, SocketException {
        initComponents();
        setLocationRelativeTo(this);
        
        login = usuario.getLoginUsuario();
        nome = usuario.getUsuario();
        
        //INSTÂNCIANDO AS CLASSES
        MaquinaCrud computador = new MaquinaCrud(dataSource);
        Disk disco = new Disk(dataSource);
        Looca looca = new Looca();
        
        //==============================COMPUTADOR==============================
        
        //OBTENDO INFORMAÇÕES DO COMPUTADOR E GUARDANDO EM VARIAVEIS
        String hostName = computador.buscarHostName();
        String endMac = computador.buscarEndMac();
        String fabricante = computador.buscarFabricante();
        Integer arquitetura = computador.buscarArquitetura();
        String sistemaOperacional = computador.buscarSO();
        
        //TRANFERINDO OS DADOS GUARDADOS DO COMPUTADOR PARA AS LABELS NA TELA
        labelHostname.setText("HostName: " + hostName);
        labelEndMac.setText("Endereço MAC: " + endMac);
        labelFabricante.setText("Fabricante: " + fabricante);
        labelArquitetura.setText("Arquitetura: " + arquitetura + " bits");
        labelSistemaOperacional.setText("Sistema Operacional: " + sistemaOperacional);
        
        //=============================COMPONENTES==============================
        
        //OBTENDO INFORMAÇÕES DOS COMPONENTES E GUARDANDO EM VARIAVEIS
        String cpu = looca.getProcessador().getNome();
        Long memoria = looca.getMemoria().getTotal() / 1000000000;
        Integer discos = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
        
        //TRANFERINDO OS DADOS GUARDADOS DOS COMPONENTES PARA AS LABELS NA TELA
        labelCpu.setText("Processador: " + cpu);
        labelMemory.setText("Memória disponivel: " + memoria + " GB");
        labelDiscos.setText("Quantidade de Discos: " + discos + " discos disponiveis");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bttRetornar = new javax.swing.JButton();
        labelHardware = new javax.swing.JLabel();
        labelHostname = new javax.swing.JLabel();
        labelEndMac = new javax.swing.JLabel();
        labelFabricante = new javax.swing.JLabel();
        labelArquitetura = new javax.swing.JLabel();
        labelSistemaOperacional = new javax.swing.JLabel();
        labelComponente = new javax.swing.JLabel();
        labelCpu = new javax.swing.JLabel();
        labelMemory = new javax.swing.JLabel();
        labelDiscos = new javax.swing.JLabel();
        logoObservium = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(610, 490));
        setMinimumSize(new java.awt.Dimension(610, 490));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bttRetornar.setBackground(new java.awt.Color(0, 0, 0));
        bttRetornar.setForeground(new java.awt.Color(255, 255, 255));
        bttRetornar.setText("Voltar");
        bttRetornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttRetornarActionPerformed(evt);
            }
        });
        getContentPane().add(bttRetornar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 450, 80, 30));

        labelHardware.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelHardware.setForeground(new java.awt.Color(255, 255, 255));
        labelHardware.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelHardware.setText("Hardware:");
        labelHardware.setPreferredSize(new java.awt.Dimension(149, 20));
        getContentPane().add(labelHardware, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 310, 20));

        labelHostname.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelHostname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 310, 20));

        labelEndMac.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelEndMac, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 310, 20));

        labelFabricante.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelFabricante, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 310, 20));

        labelArquitetura.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelArquitetura, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 310, 20));

        labelSistemaOperacional.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelSistemaOperacional, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 310, 20));

        labelComponente.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelComponente.setForeground(new java.awt.Color(255, 255, 255));
        labelComponente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelComponente.setText("Componentes:");
        labelComponente.setPreferredSize(new java.awt.Dimension(149, 20));
        getContentPane().add(labelComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 310, 20));

        labelCpu.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelCpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 310, 20));

        labelMemory.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelMemory, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 310, 20));

        labelDiscos.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(labelDiscos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 440, 310, 20));

        logoObservium.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo_Observium_Branco.png"))); // NOI18N
        getContentPane().add(logoObservium, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 310, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FundoObservium2.jpg"))); // NOI18N
        background.setText("jLabel1");
        background.setMaximumSize(new java.awt.Dimension(610, 490));
        background.setMinimumSize(new java.awt.Dimension(610, 490));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttRetornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttRetornarActionPerformed
        UsuarioCrud usuario = new UsuarioCrud(dataSource);

        String nomeUsuario = usuario.buscarNomeUsuario(login);
        usuario.setUsuario(nomeUsuario);
        TelaFuncMaq funcMaq = new TelaFuncMaq(usuario);
        this.dispose();
        funcMaq.setVisible(true);
    }//GEN-LAST:event_bttRetornarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaHardware.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaHardware.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaHardware.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaHardware.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaHardware().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton bttRetornar;
    private javax.swing.JLabel labelArquitetura;
    private javax.swing.JLabel labelComponente;
    private javax.swing.JLabel labelCpu;
    private javax.swing.JLabel labelDiscos;
    private javax.swing.JLabel labelEndMac;
    private javax.swing.JLabel labelFabricante;
    private javax.swing.JLabel labelHardware;
    private javax.swing.JLabel labelHostname;
    private javax.swing.JLabel labelMemory;
    private javax.swing.JLabel labelSistemaOperacional;
    private javax.swing.JLabel logoObservium;
    // End of variables declaration//GEN-END:variables
}
