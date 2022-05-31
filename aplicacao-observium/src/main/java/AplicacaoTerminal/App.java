package AplicacaoTerminal;

// Classe Timer, para executar funções a cada x segundos
import BancoDeDados.ConexaoBanco;
import Componentes.AlertaSlack;
import Componentes.Componente;
import Componentes.ComponenteCrud;
import Componentes.Cpu;
import Componentes.Disco;
import Componentes.Memory;
import Maquina.Maquina;
import java.util.Scanner;
import org.apache.commons.dbcp2.BasicDataSource;
// Classe Timer, para executar funções a cada x segundos
import java.util.Timer;
import java.util.TimerTask;
// Para buscar config do PC
import Maquina.MaquinaCrud;
import TelasAplicacao.TelaCaptura;
import TelasAplicacao.TelaFuncMaq;
import Usuarios.UsuarioCrud;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class App {
    static BasicDataSource dataSource = new BasicDataSource();
    static String nomeUsuario = "";
    static String usuarioLogin = "";
    static UsuarioCrud usuarioCRUD = new UsuarioCrud(dataSource);
    static Timer timer = new Timer();
    static AlertaSlack alerta = new AlertaSlack("Thread1");//definindo uma thread a parte para alerta
    static Componentes.AppMonitoramento aplicacao = new Componentes.AppMonitoramento();
    static MaquinaCrud maquinaCRUD = new MaquinaCrud(dataSource);
    
    public static void main(String[] args) throws InterruptedException, UnknownHostException, SocketException {
        menu();
    }

    public static void menu() throws InterruptedException, UnknownHostException, SocketException {
        // Declaração das variáveis   
        Scanner leitorOpcao = new Scanner(System.in);
        Scanner leitorString = new Scanner(System.in);
        Boolean exited = false;

        verificarSeComputadorEstaCadastrado();
                
        //Inicio da aplicação
        //Menu
         
        do {
            System.out.println("\nBem vindo a nossa aplicacao. Digite uma tecla "
                    + "para prosseguir: \n"
                    + "1. Inicializar aplicacao\n"
                    + "2. Ver hardware da sua maquina\n"
                    + "3. Fechar aplicacao");
            
            String opcao = "";
            opcao = leitorOpcao.next();
            
            while (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3")) {
                System.out.println("\nSelecione entre as tres opcoes validas:");
                opcao = leitorOpcao.next();
            }
            
            Integer opcaoEscolhida = Integer.valueOf(opcao);
            
            switch (opcaoEscolhida) {
                case 1:
                    System.out.println("\nDigite seu nome de usuario: ");
                    String usuario = leitorString.nextLine();
                    
                    System.out.println("\nDigite sua senha: ");
                    String senha = leitorString.nextLine();
                    
                    if (validarLogin(usuario, senha)) {
                        exited = true;
                        app();
                    } else {
                        System.out.println("\nUsuario ou senha incorretos.");
                    }
                    
                    break;
                case 2:
                    getConfiguracoesHardware();
                    break;
                case 3:
                    exited = sair();
                    break;
            }
            
        } while (!exited);
         
    }

    public static void app() throws InterruptedException, UnknownHostException, SocketException {
        Scanner leitorOpcao = new Scanner(System.in);
        
        // Exportando classe que terá o método de recolha de captura de dados
        
        Boolean exited = false;
        int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
        int interval = 10000;  // intervalo no qual a tarefa será executada.
        
        do {
            System.out.println(String.format("\nBem vindo a aplicacao, %s!\n"
                    + "1. Inicializar captura de dados\n"
                    + "2. Fechar aplicacao", nomeUsuario));
            
            String opcaoEscolhida = leitorOpcao.next();
            
            while (!opcaoEscolhida.equals("1") && !opcaoEscolhida.equals("2")) {
                System.out.println("\nSelecione entre as duas opcoes validas:");
                opcaoEscolhida = leitorOpcao.next();
            }
            
            Integer opcao = Integer.valueOf(opcaoEscolhida);
            
            switch (opcao) {
                case 1:
                    if (verificarSeComputadorEstaCadastrado() == false){
                        System.out.println("\nMaquina nao esta cadastrada... "
                                         + "\nVamos realizar o cadastramento.");
                        cadastrarMaquina();
                    } else {
                        exited = true;
                        timer.scheduleAtFixedRate(new TimerTask() {
                            public void run() {
                                try {
                                    aplicacao.aplicacao();
                                } catch (UnknownHostException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SocketException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (Exception ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                            
                        }, delay, interval);
                        
                    }
                    break;

                case 2:
                    System.out.println("\nAte mais =D");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
                    
            }
            
        } while (!exited);

    }

    public static void cadastrarMaquina() throws InterruptedException, UnknownHostException, SocketException {
        if (verificarSeComputadorEstaCadastrado()) {
            System.out.println("\nMáquina ja cadastrada! Realize login.");
        } else {
            //INSTANCIANDO A CLASSE MAQUINA CRUD
            MaquinaCrud computador = new MaquinaCrud(dataSource);
            UsuarioCrud usuario = new UsuarioCrud(dataSource);
            
            //OBTENDO INFORMAÇÕES DO COMPUTADOR E GUARDANDO EM VARIAVEIS
            String hostName = computador.buscarHostName();
            String apelidoMaq = hostName;
            String endMac = computador.buscarEndMac();
            String fabricante = computador.buscarFabricante();
            Integer arquitetura = computador.buscarArquitetura();
            String sistemaOperacional = computador.buscarSO();
            
            Boolean existPcNuvem = maquinaCRUD.verificarMaquinaNuvem(hostName);
            Boolean existPcLocal = maquinaCRUD.verificarMaquinaLocal(hostName);
            
            String localidade = menuLocalidade();
            
            //BUSCANDO O ID DO HOSPITAL DO USUÁRIO LOGADO PARA CADASTRAR A MÁQUINA
            Integer fkHospital = usuario.buscarIdHospitalNuvem(usuarioLogin);
            
            try {
                //INSERINDO COMPUTADOR NO BANCO COM AS INFORMAÇÕES OBTIDAS
                Maquina maquina = new Maquina(hostName, apelidoMaq, endMac, fabricante,
                    arquitetura, sistemaOperacional, localidade, fkHospital);
                
                if (existPcNuvem.equals(false)) {
                    computador.incluirNuvem(maquina); //INCLUIR NO BANCO NA NUVEM
                }
                
                if (existPcLocal.equals(false)) {
                    computador.incluirLocal(maquina); //INCLUIR NO BANCO LOCAL
                }
            
                //========================COMPONENTES===========================
                ComponenteCrud componentes = new ComponenteCrud(dataSource);

                Cpu cpu = new Cpu();
                Memory memory = new Memory();
                Disco disco = new Disco();

                Integer idPcNuvem = maquinaCRUD.buscarIdComputadorNuvem(hostName);
                Integer idPcLocal = maquinaCRUD.buscarIdComputadorLocal(hostName);

                //INSERINDO CPU NO LOCAL
                String nomeCpu = cpu.buscarNomeCpu();

                if (existPcNuvem.equals(false)) {
                    Componente cpuNuvem = new Componente(nomeCpu, "CPU", idPcNuvem);
                    componentes.incluirComponenteNuvem(cpuNuvem); //INCLUIR NA NUVEM
                }

                if (existPcLocal.equals(false)) {
                    Componente cpuLocal = new Componente(nomeCpu, "CPU", idPcLocal);
                    componentes.incluirComponenteLocal(cpuLocal); //INCLUIR NO BANCO
                }

                //INSERINDO MEMÓRIA
                String nomeMemoria = memory.buscarNomeMemoria();

                if (existPcNuvem.equals(false)) {
                    Componente memoriaNuvem = new Componente(nomeMemoria, "MEMORIA", idPcNuvem);
                    componentes.incluirComponenteNuvem(memoriaNuvem); //INCLUIR NA NUVEM
                }

                if (existPcLocal.equals(false)) {
                    Componente memoriaLocal = new Componente(nomeMemoria, "MEMORIA", idPcLocal);
                    componentes.incluirComponenteLocal(memoriaLocal); //INCLUIR NO BANCO
                }

                //INSERINDO DISCO
                String nomeDisco = disco.buscarNomeDisco();

                if (existPcNuvem.equals(false)) {
                    Componente discoNuvem = new Componente(nomeDisco, "DISCO", idPcNuvem);
                    componentes.incluirComponenteNuvem(discoNuvem); //INCLUIR NA NUVEM
                }

                if (existPcLocal.equals(false)) {
                    Componente discoLocal = new Componente(nomeDisco, "DISCO", idPcLocal);
                    componentes.incluirComponenteLocal(discoLocal); //INCLUIR NO BANCO
                }

                if (existPcLocal.equals(false) && existPcNuvem.equals(false)) {
                    System.out.println("\nMáquina cadastrada com sucesso"
                            + "\nna Nuvem e no Banco Local!");
                } else if (existPcLocal.equals(false)) {
                    System.out.println("\nMáquina cadastrada no"
                            + "\nBanco Local com sucesso!");
                } else {
                    System.out.println("\nMáquina cadastrada na"
                            + "\nNuvem com sucesso!");
                }
                
            } catch (org.springframework.dao.DuplicateKeyException exception) {
                System.out.println("\nMáquina já cadastrada na"
                        + "\nNuvem e no Banco Local!");
            }
                
        }
        
    }
    
    public static Boolean sair() {
        Boolean exited = false;
        
        do {
            Scanner leitorOpcao = new Scanner(System.in);
            System.out.println("\nVoce tem certeza que deseja encerrar a "
                    + "aplicacao? \n"
                    + "1. Sim\n"
                    + "2. Não");
            Integer opcaoEscolhida = leitorOpcao.nextInt();

            switch (opcaoEscolhida) {
                case 1:
                    System.out.println("\nEspero te ver novamente por aqui! Ate mais"
                            + "\n");
                    System.exit(0);
                    return true;

                case 2:
                    System.out.println("\nVoltando ao menu principal..."
                            + "\n");
                    return false;
                default:
                    System.out.println("\nOpcao invalida ,tente novamente."
                            + "\n");
            }
        } while (!exited);

        return false;
    }

    public static Boolean verificarSeComputadorEstaCadastrado() throws InterruptedException, UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        MaquinaCrud maquinaCRUD = new MaquinaCrud(dataSource);
        String hostname = maquinaCRUD.buscarHostName();
        ComponenteCrud compCRUD = new ComponenteCrud(dataSource);
        
        if (maquinaCRUD.buscarIdComputadorNuvem(hostname) != 0) {
            if(maquinaCRUD.buscarIdComputadorLocal(hostname) != 0){
                 return true;
            }
           return false;
        } else {
            return false;
        }
        
    }
        
    public static Boolean validarLogin(String usuario, String senha) {
        BasicDataSource dataSource = new BasicDataSource();
        UsuarioCrud usuarioCRUD = new UsuarioCrud(dataSource);
        
        //CHAMANDO UM MÉTODO PARA VALIDAR O USUÁRIO QUE ESTÁ TENTANDO LOGAR
        String login = usuarioCRUD.validarUsuario(usuario, senha).toString().replace("[{count(login)=", "");
        login = login.replace("[{=", "");
        login = login.replace("}]", "");

        switch (login) {
            case "1":
                usuarioLogin = usuario;
                nomeUsuario = usuarioCRUD.buscarNomeUsuario(usuario);
                ConexaoBanco conexao = new ConexaoBanco();
                
                // Inserindo o usuário no local
                List<String> dadosUsuario = usuarioCRUD.inserindoUsuarioNoLocal(usuarioLogin);
                
                if (!(dadosUsuario.size() == 0)) {
                    String email = dadosUsuario.get(0);
                    String setor = dadosUsuario.get(1);
                    String tipoUsuario = dadosUsuario.get(2);
                    String fkHospital = dadosUsuario.get(3);
                    
                    conexao.getConexaoLocal().update("Insert into Usuario (nome, email, setor, tipoUsuario, login, senha, fkHospital) "
                        + "values (?, ?, ?, ?, ?, ?, ?)",
                        nomeUsuario, email, setor, tipoUsuario, usuarioLogin, senha, fkHospital);
                }
                
                return true;
                
            default:
                return false;
        }
    }
    
    public static String menuLocalidade() throws UnknownHostException{
        Boolean exited = false;
        Scanner leitorOpcao = new Scanner(System.in);
        Scanner leitorString = new Scanner(System.in);
        MaquinaCrud maquinaCrud = new MaquinaCrud(dataSource);
        
        String hostName = maquinaCrud.buscarHostName();
        String localidade = "";
        
        Boolean existPcNuvem = maquinaCrud.verificarMaquinaNuvem(hostName);
        Boolean existPcLocal = maquinaCrud.verificarMaquinaLocal(hostName);
        
        if (existPcNuvem.equals(true) || existPcLocal.equals(true)) {
            
            if (existPcNuvem.equals(true)) {
                localidade = maquinaCrud.buscarLocalMaqNuvem(hostName);
            } else {
                localidade = maquinaCrud.buscarLocalMaqLocal(hostName);
            }
            
            System.out.println("\nLocalidade já registrada: " + localidade);
            
        } else {
            
            do {            
                System.out.println("\nSelecione o seu setor:"
                        + "\n1. Recepcao"
                        + "\n2. Triagem"
                        + "\n3. Ala médica");
                
                String opcao = leitorOpcao.next();
                
                while (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3")) {
                    System.out.println("\nEscolha apenas entre as três opções validas:");
                    opcao = leitorOpcao.next();
                }
                
                Integer opcaoEscolhida = Integer.valueOf(opcao);
                
                System.out.println("\nDigite o número do seu andar: ");
                String andar = leitorString.nextLine();
                
                switch (opcaoEscolhida) {
                    case 1:
                        localidade = "recepcao-" + andar;
                        return localidade;
                    case 2:
                        localidade = "triagem-" + andar;
                        return localidade;
                    case 3:
                        localidade  = "ala-medica-" + andar;
                        return localidade;
                    default:
                        System.out.println("\nOpção inválida! Tente novamente");
                }
            
            } while (!exited);
            
        }
        
        return localidade;
        
    }
    
    public static void getConfiguracoesHardware() throws InterruptedException, UnknownHostException, SocketException{
        
        System.out.println(String.format("\nConfiguracoes da sua maquina:"
                    + "\nEndereco MAC: %s "
                    + "\nFabricante: %s"
                    + "\nHostname: %s"
                    + "\nArquitetura: %s bits"
                    + "\nSistema operacional: %s",
                    maquinaCRUD.buscarEndMac(),
                    maquinaCRUD.buscarFabricante(),
                    maquinaCRUD.buscarHostName(),
                    maquinaCRUD.buscarArquitetura(),
                    maquinaCRUD.buscarSO()));
        
    }
    
}
