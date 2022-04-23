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
import org.springframework.jdbc.core.JdbcTemplate;
import java.awt.Color;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author fabio.gdesouza
 */
public class TelaFuncMaq extends javax.swing.JFrame {
    private JdbcTemplate jdbcTemplate;
    public String login;
    public String nome;
    
    public TelaFuncMaq(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /**
     * Creates new form TelaFuncMaq
     */
    public TelaFuncMaq() {
        initComponents();
        setLocationRelativeTo(this);
    }
    
    public TelaFuncMaq(UsuarioCrud usuario) {
        initComponents();
        setLocationRelativeTo(this);
        
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Dan-auto85");
        
        login = usuario.getLoginUsuario();
        nome = usuario.getUsuario();
        String texto = "Bem vindo(a) " + nome + "!";
        labelNomeUsuario.setText(texto);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelMostrarErro = new javax.swing.JLabel();
        labelNomeUsuario = new javax.swing.JLabel();
        labelIniciar = new javax.swing.JLabel();
        bttIniciar = new javax.swing.JButton();
        labelCadastrar = new javax.swing.JLabel();
        bttCadastrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelMostrarErro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelMostrarErro.setForeground(new java.awt.Color(204, 0, 0));
        labelMostrarErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(labelMostrarErro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 450, 70));

        labelNomeUsuario.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        labelNomeUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelNomeUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNomeUsuario.setText("Bem Vindo!");
        labelNomeUsuario.setPreferredSize(new java.awt.Dimension(149, 20));
        jPanel1.add(labelNomeUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 450, 40));

        labelIniciar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelIniciar.setForeground(new java.awt.Color(255, 255, 255));
        labelIniciar.setText("Iniciar Aplicação Java:");
        jPanel1.add(labelIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 250, 50));

        bttIniciar.setBackground(new java.awt.Color(0, 0, 0));
        bttIniciar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bttIniciar.setForeground(new java.awt.Color(255, 255, 255));
        bttIniciar.setText("Cadastrar");
        bttIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttIniciarActionPerformed(evt);
            }
        });
        jPanel1.add(bttIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 250, 40));

        labelCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        labelCadastrar.setText("Cadastrar Máquina:");
        jPanel1.add(labelCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 250, 50));

        bttCadastrar.setBackground(new java.awt.Color(0, 0, 0));
        bttCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        bttCadastrar.setText("Start");
        jPanel1.add(bttCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 250, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo_Observium_Branco.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FundoObservium2.jpg"))); // NOI18N
        background.setText("jLabel1");
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    private void bttIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttIniciarActionPerformed
        try {
            BasicDataSource dataSource = new BasicDataSource();
            UsuarioCrud usuario = new UsuarioCrud(dataSource);
            
            //CONFIGURAÇÕES DO BANCO
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
            dataSource.setUsername("root");
            dataSource.setPassword("Dan-auto85");
            
            //==========================COMPUTADOR==============================
            
            //INSTANCIANDO A CLASSE MAQUINA CRUD
            MaquinaCrud computador = new MaquinaCrud(dataSource);
            
            //OBTENDO INFORMAÇÕES DO COMPUTADOR E GUARDANDO EM VARIAVEIS
            String hostName = computador.buscarHostName();
            String endMac = computador.buscarEndMac();
            String fabricante = computador.buscarFabricante();
            Integer arquitetura = computador.buscarArquitetura();
            String sistemaOperacional = computador.buscarSO();
            
            //BUSCANDO O ID DO HOSPITAL DO USUÁRIO LOGADO PARA CADASTRAR A MÁQUINA
            List idHosp = usuario.buscarIdHospital(login);
            String idHospital = String.valueOf(idHosp);
            idHospital = idHospital.replace("[{fkHospital=", "");
            idHospital = idHospital.replace("}]", "");
            
            Integer fkHospital = Integer.valueOf(idHospital);
            
            try {
                //INSERINDO COMPUTADOR NO BANCO COM AS INFORMAÇÕES OBTIDAS
                Maquina maquina = new Maquina(hostName, endMac, fabricante, 
                        arquitetura, sistemaOperacional, fkHospital);
                
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
            }
            catch (org.springframework.dao.DuplicateKeyException exception) {
                labelMostrarErro.setForeground(Color.red);
                labelMostrarErro.setText("Máquina já cadastrada.");
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(TelaFuncMaq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(TelaFuncMaq.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_bttIniciarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaFuncMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaFuncMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaFuncMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFuncMaq.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaFuncMaq().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton bttCadastrar;
    private javax.swing.JButton bttIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelCadastrar;
    private javax.swing.JLabel labelIniciar;
    private javax.swing.JLabel labelMostrarErro;
    private javax.swing.JLabel labelNomeUsuario;
    // End of variables declaration//GEN-END:variables
}
