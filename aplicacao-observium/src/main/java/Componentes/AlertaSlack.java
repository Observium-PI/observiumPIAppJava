package Componentes;

import BancoDeDados.ConexaoBanco;
import Maquina.MaquinaCrud;
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
import java.util.ArrayList;
import java.util.List;

//precisa herdar do TimerTask para rodar as funções da biblioteca
public class AlertaSlack {

    private String name;
    ConexaoBanco conexao = new ConexaoBanco();
    BasicDataSource dataSource = new BasicDataSource();
    AlertaSlackCrud alerta = new AlertaSlackCrud(dataSource);
    MaquinaCrud maquinaCrud = new MaquinaCrud(dataSource);
    Looca looca = new Looca();

    //construtor que recebe o nome da thread
    public AlertaSlack(String n) {
        this.name = n;
    }
    
    public void validaAlerta(Integer medidaCpu, Integer medidaMemoria, Integer medidaDisco) throws Exception {
        try {
            String tipoAlerta = "";
            String nivelAlerta = "";
            String tipoComponente = "";
            String hostname = maquinaCrud.buscarHostName();
            
            List<Integer> listaComponente = new ArrayList();
            
            listaComponente.add(medidaCpu);
            listaComponente.add(medidaMemoria);
            listaComponente.add(medidaDisco);
            
            for (int i = 0; i < listaComponente.size(); i++) {
                if (i == 0) {
                    tipoAlerta = "Processamento";
                    tipoComponente = "CPU";
                } else if (i == 1) {
                    tipoAlerta = "Uso";
                    tipoComponente = "Memória";
                } else {
                    tipoAlerta = "Armazenamento";
                    tipoComponente = "Disco";
                }
                
                if (listaComponente.get(i) >= 65 && listaComponente.get(i) <= 75) {
                    nivelAlerta = "médio";
                    enviarAlerta(tipoAlerta, nivelAlerta, tipoComponente, listaComponente.get(i), hostname);
                } else if (listaComponente.get(i) >= 75 && listaComponente.get(i) <= 85) {
                    nivelAlerta = "alto";
                    enviarAlerta(tipoAlerta, nivelAlerta, tipoComponente, listaComponente.get(i), hostname);
                } else if (listaComponente.get(i) > 85) {
                    nivelAlerta = "crítico";
                    enviarAlerta(tipoAlerta, nivelAlerta, tipoComponente, listaComponente.get(i), hostname);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enviarAlerta(
            String tipoAlerta,
            String nivelAlerta,
            String tipoComponente,
            Integer uso,
            String hostname
    ) throws Exception {
        try {
            Slack slack = Slack.getInstance();
            String txtReforco = "";
            
            if (tipoComponente.equalsIgnoreCase("CPU")) {
                if (nivelAlerta.equalsIgnoreCase("médio")) {
                    txtReforco = "com uso acima da média!";
                } else if (nivelAlerta.equalsIgnoreCase("alto")) {
                    txtReforco = "necessitando de uma atenção maior!";
                } else {
                    txtReforco = "com uso excessivo de processamento!";
                }
            } else if (tipoComponente.equalsIgnoreCase("Memória")) {
                if (nivelAlerta.equalsIgnoreCase("médio")) {
                    txtReforco = "em uso acima da média!";
                } else if (nivelAlerta.equalsIgnoreCase("alto")) {
                    txtReforco = "necessitando de uma atenção maior!";
                } else {
                    txtReforco = "em uso perto do seu limite!";
                }
            } else {
                if (nivelAlerta.equalsIgnoreCase("médio")) {
                    txtReforco = "com armazenamento acima da metade!";
                } else if (nivelAlerta.equalsIgnoreCase("alto")) {
                    txtReforco = "com armazenamento quase cheio!";
                } else {
                    txtReforco = "com armazenamento beirando o total!";
                }
            }
            
            String msg = String.format(""
                    + "Alerta %s: %s %s"
                    + "\n%s atual de %s: %d%%"
                    + "\nHostname: %s",
                    nivelAlerta, tipoComponente, txtReforco, tipoAlerta, 
                        tipoComponente, uso, hostname);
            
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
            
            //registra o alerta na tabela Historico
            alerta.resgatarIdMonitoramentoLocal(msg, hostname, dataHora);
            alerta.resgatarIdMonitoramentoNuvem(msg, hostname, dataHora);

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
