package AplicacaoTerminal;

import Componentes.AlertaSlack;
import Componentes.Componente;
import Componentes.ComponenteCrud;
import Componentes.Cpu;
import Maquina.Maquina;
import java.util.Scanner;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
// Classe Timer, para executar funções a cada x segundos
import java.util.Timer;
import java.util.TimerTask;
// Para buscar config do PC
import Maquina.MaquinaCrud;
import TelasAplicacao.TelaCaptura;
import Usuarios.UsuarioCrud;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    static BasicDataSource dataSource = new BasicDataSource();
    static String  nomeUsuario = "";
    static String usuarioLogin = "";
    static UsuarioCrud usuarioCRUD = new UsuarioCrud(dataSource);
    static Timer timer = new Timer();
    static AlertaSlack alerta = new AlertaSlack("Thread1");//definindo uma thread a parte para alerta
    static Componentes.App aplicacao = new Componentes.App();
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

        /* Inicio da aplicação
            Menu
         */
        do {
            System.out.println("\nBem vindo à nossa aplicação. Digite uma tecla p"
                    + "ara progressuir: \n"
                    + "1. Inicializar aplicação\n"
                    + "2. Ver hardware da sua máquina\n"
                    + "3. Fechar aplicação");
            Integer opcaoEscolhida = leitorOpcao.nextInt();

            switch (opcaoEscolhida) {
                case 1:
                    System.out.println("\nDigite seu nome de usuário");
                    String usuario = leitorString.nextLine();

                    System.out.println("\nDigite sua senha: ");
                    String senha = leitorString.nextLine();

                    if (validarLogin(usuario, senha)) {
                        exited = true;
                        app();
                    } else {
                        System.out.println("\nUsuário ou senha incorretos.");
                    }

                    break;

                case 2:
                    getConfiguracoesHardware();
                    break;
                case 3:
                    exited = sair();
                    break;
                default:
                    System.out.println("\nOpção inválida");
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
            System.out.println(String.format("\n\n\nBem vindo à aplicação, %s!\n"
                    + "1. Inicializar captura de dados\n"
                    + "2. Fechar aplicação", nomeUsuario));
            Integer opcao = leitorOpcao.nextInt();

            switch (opcao) {
                case 1:   
                    if(verificarSeComputadorEstaCadastrado() == false){
                        System.out.println("\nMáquina não está cadastrada...");
                        cadastrarMaquina();
                        
                    }else{ 
                        exited = true;
                    timer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            try {
                                aplicacao.aplicacao();
                            } catch (UnknownHostException ex) {
                                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SocketException ex) {
                                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, delay, interval);
                    }
                    break;

                case 2:
                    System.out.println("Até mais =D");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;

            }
        } while (!exited);

    }

    public static void cadastrarMaquina() throws InterruptedException, UnknownHostException, SocketException {
       
            
        if (verificarSeComputadorEstaCadastrado()) {
            System.out.println("\nMáquina já cadastrada! Realize login.");
        } else {
            Scanner leitorString = new Scanner(System.in);

            //Utilizando o Looca para obter informações do computador
            String enderecoMAC = maquinaCRUD.buscarEndMac();
            String fabricante = maquinaCRUD.buscarFabricante();
            String hostname = maquinaCRUD.buscarHostName();
            Integer arquitetura = maquinaCRUD.buscarArquitetura();
            String SO = maquinaCRUD.buscarSO();
            // Mudar essa localidade
            String localidade = menuLocalidade();
            // Mudar o hospital
            String hospitalString = usuarioCRUD.buscarIdHospital(usuarioLogin).get(0).toString();
            String resultado = "";
            resultado = hospitalString.replace("{", "");
            resultado = resultado.replace("}", "");
            resultado = resultado.replace("fkHospital=", "");
            Integer hospital = Integer.parseInt(resultado);

            // Criando instância do objeto máquina 
            Maquina maquina = new Maquina(hostname, enderecoMAC, fabricante, arquitetura, SO, localidade, hospital);

            System.out.println("\nInicializando cadastro da máquina");
            Thread.sleep(5000);

            

            maquinaCRUD.incluir(maquina);

            // Inserindo componentes
            //========================COMPONENTES===========================
            ComponenteCrud componentes = new ComponenteCrud(dataSource);

            //BUSCANDO ID DO COMPUTADOR CADASTRADO
            List idPc = componentes.buscarIdComputador(hostname);
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

            System.out.println("Máquina cadastrada com sucesso!");
        }
    }

    public static Boolean sair() {
        Boolean exited = false;

        do {
            Scanner leitorOpcao = new Scanner(System.in);
            System.out.println("\nVocê tem certeza que deseja encerrar a apli"
                    + "cação? \n"
                    + "1. Sim\n"
                    + "2. Não");
            Integer opcaoEscolhida = leitorOpcao.nextInt();

            switch (opcaoEscolhida) {
                case 1:
                    System.out.println("\nEspero te ver novamente por aqui! Até mais"
                            + "\n");
                    System.exit(0);
                    return true;

                case 2:
                    System.out.println("\nVoltando ao menu principal..."
                            + "\n");
                    return false;
                default:
                    System.out.println("\nOpção inválida ,tente novamente."
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

        if (compCRUD.buscarIdComputador(hostname).size() != 0) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean validarLogin(String usuario, String senha) {
        BasicDataSource dataSource = new BasicDataSource();

        UsuarioCrud usuarioCRUD = new UsuarioCrud(dataSource);

        //CHAMANDO UM MÉTODO PARA VALIDAR O USUÁRIO QUE ESTÁ TENTANDO LOGAR
        String login = usuarioCRUD.validarUsuario(usuario, senha).toString().replace("[{count(login)=", "");
        login = login.replace("}, {count(login)=", "");
        login = login.replace("}]", "");

        switch (login) {
            case "1":
                usuarioLogin = usuario;
                nomeUsuario = usuarioCRUD.buscarNomeUsuario(usuario);
                return true;

            default:
                return false;
        }
    }
    
    public static String menuLocalidade(){
        Boolean exited = false;
        Scanner leitorOpcao = new Scanner(System.in);
        Scanner leitorString = new Scanner(System.in);
        
        
        do {            
            System.out.println("\nSelecione o seu setor: \n"
                    + "1. Recepção\n"
                    + "2. Triagem\n"
                    + "3. Ala médica");
            
            Integer opcaoEscolhida = leitorOpcao.nextInt();
            
            System.out.println("Digite o número do seu andar: ");
            String andar = leitorString.nextLine();
            
            switch (opcaoEscolhida) {
                case 1:
                    return "recepcao-" + andar;
                case 2:
                    return "triagem-" + andar;
                case 3:
                    return "ala-medica-" + andar;
                default:
                    System.out.println("\nOpção inválida!, tente novamente");
            }
            
        } while (!exited);
        
        return "sem-setor";
    }
    
    public static void getConfiguracoesHardware() throws InterruptedException, UnknownHostException, SocketException{
        
        System.out.println(String.format("\nConfigurações da sua máquina:"
                    + "\nEndereço MAC: %s "
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
