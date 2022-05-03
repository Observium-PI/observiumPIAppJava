package TelasAplicacao;

import Maquina.Maquina;
import Maquina.MaquinaCrud;
import Componentes.Componente;
import Componentes.ComponenteCrud;
import Usuarios.UsuarioCrud;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import java.awt.Color;

/**
 *
 * @author aluno
 */
public class TelaCadMaq extends javax.swing.JFrame {

    public String login;
    public String nome;
    
    BasicDataSource dataSource = new BasicDataSource();
    
    public TelaCadMaq() {
        initComponents();
        setLocationRelativeTo(this);
    }
    
    public TelaCadMaq(UsuarioCrud usuario) {
        initComponents();
        setLocationRelativeTo(this);

        login = usuario.getLoginUsuario();
        nome = usuario.getUsuario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        setor = new javax.swing.JLabel();
        andar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelMostrarErro = new javax.swing.JLabel();
        bttCadastrar = new javax.swing.JButton();
        comboBoxSetor = new javax.swing.JComboBox<>();
        numeroAndar = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Localidade da máquina");
        titulo.setPreferredSize(new java.awt.Dimension(149, 20));
        getContentPane().add(titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 450, 40));

        setor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        setor.setForeground(new java.awt.Color(255, 255, 255));
        setor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        setor.setText("Setor:");
        setor.setPreferredSize(new java.awt.Dimension(149, 20));
        getContentPane().add(setor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 70, 40));

        andar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        andar.setForeground(new java.awt.Color(255, 255, 255));
        andar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        andar.setText("Andar:");
        andar.setPreferredSize(new java.awt.Dimension(149, 20));
        getContentPane().add(andar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 60, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo_Observium_Branco.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        labelMostrarErro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelMostrarErro.setForeground(new java.awt.Color(204, 0, 0));
        labelMostrarErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(labelMostrarErro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 450, 70));

        bttCadastrar.setBackground(new java.awt.Color(0, 0, 0));
        bttCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bttCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        bttCadastrar.setText("Concluir Cadastro");
        bttCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttCadastrarActionPerformed(evt);
            }
        });
        getContentPane().add(bttCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 190, 40));

        comboBoxSetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Recepção", "Triagem", "Ala médica" }));
        comboBoxSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSetorActionPerformed(evt);
            }
        });
        getContentPane().add(comboBoxSetor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 190, 40));

        numeroAndar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numeroAndar.setText("00");
        getContentPane().add(numeroAndar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, 60, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FundoObservium2.jpg"))); // NOI18N
        background.setText("jLabel1");
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSetorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSetorActionPerformed

    private void bttCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttCadastrarActionPerformed
        try {
            UsuarioCrud usuario = new UsuarioCrud(dataSource);

            //==========================COMPUTADOR==============================
            //INSTANCIANDO A CLASSE MAQUINA CRUD
            MaquinaCrud computador = new MaquinaCrud(dataSource);

            //OBTENDO INFORMAÇÕES DO COMPUTADOR E GUARDANDO EM VARIAVEIS
            String hostName = computador.buscarHostName();
            String endMac = computador.buscarEndMac();
            String fabricante = computador.buscarFabricante();
            Integer arquitetura = computador.buscarArquitetura();
            String sistemaOperacional = computador.buscarSO();
            String localidade = comboBoxSetor.getName();

            //BUSCANDO O ID DO HOSPITAL DO USUÁRIO LOGADO PARA CADASTRAR A MÁQUINA
            List idHosp = usuario.buscarIdHospital(login);
            String idHospital = String.valueOf(idHosp);
            idHospital = idHospital.replace("[{fkHospital=", "");
            idHospital = idHospital.replace("}]", "");

            Integer fkHospital = Integer.valueOf(idHospital);

            try {
                //INSERINDO COMPUTADOR NO BANCO COM AS INFORMAÇÕES OBTIDAS
                Maquina maquina = new Maquina(hostName, endMac, fabricante,
                    arquitetura, sistemaOperacional, localidade, fkHospital);

                computador.incluir(maquina); //INCLUIR NO BANCO

                //========================COMPONENTES===========================
                ComponenteCrud componentes = new ComponenteCrud(dataSource);

                //BUSCANDO ID DO COMPUTADOR CADASTRADO
                List idPc = componentes.buscarIdComputador(hostName);
                String idMaquina = String.valueOf(idPc);
                idMaquina = idMaquina.replace("[{idComputador=", "");
                idMaquina = idMaquina.replace("}]", "");

                Integer idComputadores = Integer.valueOf(idMaquina);

                String haveCpu = componentes.buscarCpuComponente();
                String haveMemoria = componentes.buscarMemoriaComponente();

                //INSERINDO CPU
                Componente cpu = new Componente(haveCpu, idComputadores);
                componentes.incluirComponente(cpu); //INCLUIR NO BANCO

                //INSERINDO MEMÓRIA
                if (haveMemoria != null) {
                    Componente memoria = new Componente(haveMemoria, idComputadores);
                    componentes.incluirComponente(memoria); //INCLUIR NO BANCO
                } else {
                    System.out.println("Sem memória");
                }

                //INSERINDO DISCOS
                Integer haveDisco = componentes.buscarVolumesComponente();

                for (int i = 0; i < haveDisco; i++) {
                    Componente disco = new Componente("disco " + (i + 1), idComputadores);
                    componentes.incluirComponente(disco); //INCLUIR NO BANCO
                }

                labelMostrarErro.setForeground(Color.green);
                labelMostrarErro.setText("Máquina cadastrada com sucesso!");
            } catch (org.springframework.dao.DuplicateKeyException exception) {
                labelMostrarErro.setForeground(Color.red);
                labelMostrarErro.setText("Máquina já cadastrada.");
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(TelaFuncMaq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(TelaFuncMaq.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bttCadastrarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadMaq().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel andar;
    private javax.swing.JLabel background;
    private javax.swing.JButton bttCadastrar;
    private javax.swing.JComboBox<String> comboBoxSetor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelMostrarErro;
    private javax.swing.JTextField numeroAndar;
    private javax.swing.JLabel setor;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}