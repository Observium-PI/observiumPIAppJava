package Componentes;

import BancoDeDados.ConexaoBanco;
import com.github.britooo.looca.api.core.Looca;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import java.net.InetAddress;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//precisa herdar do TimerTask para rodar as funções da biblioteca
public class AlertaSlack extends TimerTask {

    private String name;
    ConexaoBanco conexao = new ConexaoBanco();
    BasicDataSource dataSource = new BasicDataSource();
    AlertaSlackCrud alerta = new AlertaSlackCrud(dataSource);
    Looca looca = new Looca();

    //construtor que recebe o nome da thread
    public AlertaSlack(String n) {
        this.name = n;
    }

    //função do TimerTask que roda o código principal da classe
    //ou seja, o run roda a função que verifica a thread e se for a thread certa
    //executa a função inicar() e pausa por 1 seg a thread atual
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " " + name);
        if ("Thread1".equalsIgnoreCase(name)) {
            try {
                iniciar();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(AlertaSlack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void iniciar() throws Exception {
        resgatar();
    }

    public void resgatar() throws Exception {

        Disk disk = new Disk(dataSource);
        Cpu cpu = new Cpu(dataSource);
        Memory ram = new Memory(dataSource);

        Double medidaCpu = cpu.looca.getProcessador().getUso();
        String tipoComponente = "";
        Double uso = 0.0;

        //Definindo o uso e o tipo do componente como o do CPU do pc atual e
        //enviando para a validação de alerta
        uso = medidaCpu;
        tipoComponente = "CPU";

        validaAlerta(tipoComponente, uso);

        //Definindo o uso e o tipo do componente como o do RAM do pc atual e
        //enviando para a validação de alerta
        uso = ram.memoriaEmUso() * 100 / ram.memoriaTotal();
        tipoComponente = "RAM";

        validaAlerta(tipoComponente, uso);

        //Definindo o uso e o tipo do componente como o do DISCO do pc atual e
        //enviando para a validação de alerta
        //Convertendo a lista com a quantidade de gigas usado e total disponivel
        //de list para string e de string para double
        for (int i = 0; i < disk.qtdDiscos(); i++) {
            String totalTxt = disk.totalDisco().get(i).toString();
            String atualTxt = disk.disponivelDisco().get(i).toString();

            totalTxt = totalTxt.replace("[", "");
            totalTxt = totalTxt.replace("]", "");
            atualTxt = atualTxt.replace("[", "");
            atualTxt = atualTxt.replace("]", "");

            Double atualDouble = Double.parseDouble(atualTxt);
            Double totalDouble = Double.parseDouble(totalTxt);

            //calculando porcentagem de uso
            uso = totalDouble - atualDouble;
            uso = (uso * 100) / totalDouble;
            tipoComponente = "DISCO";

            validaAlerta(tipoComponente, uso);
        }
    }

    public void validaAlerta(String tipoComponente, Double uso) throws Exception {
        try {
            String tipoAlerta = "";
            String nivelAlerta = "";
            String txtReforco = "";
            String hostname = InetAddress.getLocalHost().getHostName();

            //Definindo o componente do alerta (tipo)
            if (tipoComponente.equals("CPU")) {
                tipoAlerta = "Temperatura";
            } else if (tipoComponente.equals("DISCO")) {
                tipoAlerta = "Armazenamento";
            } else if (tipoComponente.equals("RAM")) {
                tipoAlerta = "Uso";
            }

            //Definindo o nivel do alerta e txt de reforço do alerta
            if (uso >= 65 && uso <= 75) {
                nivelAlerta = "médio";
                txtReforco = "acima da média!";
                enviarAlerta(txtReforco, tipoAlerta, nivelAlerta, tipoComponente, uso, hostname);
            } else if (uso >= 75 && uso <= 85) {
                nivelAlerta = "alto";
                txtReforco = "necessitando de uma atenção maior!";
                enviarAlerta(txtReforco, tipoAlerta, nivelAlerta, tipoComponente, uso, hostname);
            } else if (uso > 85) {
                nivelAlerta = "crítico";
                txtReforco = "prestes a parar de funcionar!";
                enviarAlerta(txtReforco, tipoAlerta, nivelAlerta, tipoComponente, uso, hostname);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enviarAlerta(
            String txtReforco,
            String tipoAlerta,
            String nivelAlerta,
            String tipoComponente,
            Double uso,
            String hostname
    ) throws Exception {
        try {
            Slack slack = Slack.getInstance();

            String msg = String.format("Alerta %s : %s com"
                    + " %s %s\n%s atual de %s: %.2f%%"
                    + "\nHostname: %s",
                    nivelAlerta, tipoComponente, tipoAlerta, txtReforco, tipoAlerta, tipoComponente, uso, hostname);

            // Inicializa um metódo cliente API com o token especificado
            MethodsClient methods = slack.methods("");

            // Constroi um objeto de requisição
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("#geral") // Use a channel ID `C1234567` is preferrable
                    .text(msg)
                    .build();

            // Pega uma resposta como um objeto java
            ChatPostMessageResponse response = methods.chatPostMessage(request);

            DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String dataHora = dataFormat.format(LocalDateTime.now());

            //formatando o tipo do componente para fazer o select certo
            if (tipoComponente == "RAM") {
                tipoComponente = "memoriaRAM";
            } else if (tipoComponente == "DISCO") {
                tipoComponente = "disco";
            }

            //registra o alerta na tabela Historico
            alerta.resgatarIdMonitoramento(msg, hostname, dataHora, tipoComponente);

            msg += "\nData do ocorrido: " + dataHora;
            String sistemaOperacional = looca.getSistema().getSistemaOperacional();

            //Definido caminho e chamando função para guardar o log no pc atual
            if (sistemaOperacional == "Windows") {
                String path = "C:/temp/LogPc.txt";
                escritor(path, msg);

            } else if (sistemaOperacional == "Debian GNU/Linux") {
                String path = "home/LogPc.txt";
                escritor(path, msg);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Criação da pasta dos logs
    public static void escritor(String path, String msg) throws IOException {
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path, true))) {
            buffWrite.append(msg + "\n");
            buffWrite.close();
        }
    }

}
