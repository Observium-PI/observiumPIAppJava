package Componentes;

import Maquina.Maquina;
import Maquina.MaquinaCrud;
import org.apache.commons.dbcp2.BasicDataSource;
import Monitoramento.MonitoramentoCrud;
import Monitoramento.Monitoramento;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppMonitoramento {
    public void aplicacao() throws UnknownHostException, SocketException, Exception {
        BasicDataSource dataSource = new BasicDataSource();
        
        //INSTÂNCIANDO AS CLASSES
        Disco disco = new Disco();
        Memory memoria = new Memory();
        Cpu cpu = new Cpu();
        MaquinaCrud maquina = new MaquinaCrud(dataSource);
        MonitoramentoCrud monitorar = new MonitoramentoCrud(dataSource);
        AlertaSlack alerta = new AlertaSlack("Alerta Monitoramento");
        
        //FORMATANDO A DATA E HORA PARA GUARDAR NO BANCO DE FORMA CORRETA
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dataHora = dataFormat.format(LocalDateTime.now());
        
        //BUSCANDO O HOSTNAME DA MÁQUINA ATUAL PARA UTILIZAR NOS SELECTS
        String hostname = maquina.buscarHostName();
        Integer idComputadorLocal = maquina.buscarIdComputadorLocal(hostname);
        Integer idComputadorNuvem = maquina.buscarIdComputadorNuvem(hostname);
        
        //CONVERTENDO VALORES DE MEMORIA E DISCO PARA PORCENTAGEM
        Integer memoriaTotal = memoria.memoriaTotal();
        Integer memoriaEmUso = memoria.memoriaEmUso();
        
        Integer totalDisco = disco.buscarTotalDisco();
        Integer disponivelEmDisco = disco.buscarDisponivelDisco();
        Integer discoEmUso = totalDisco - disponivelEmDisco;
        
        Integer usoProcessador = cpu.usoProcessador();
        Integer usoMemoria = (memoriaEmUso * 100) / memoriaTotal;
        Integer usoDisco = (discoEmUso * 100) / totalDisco;
        
        Monitoramento monitoramentoLocal = new Monitoramento(usoProcessador, usoMemoria, 
                usoDisco, dataHora, idComputadorLocal);
        
        Monitoramento monitoramentoNuvem = new Monitoramento(usoProcessador, usoMemoria, 
                usoDisco, dataHora, idComputadorNuvem);
        
        monitorar.incluirMonitoramentoLocal(monitoramentoLocal);
        monitorar.incluirMonitoramentoNuvem(monitoramentoNuvem);
        
        List<Integer> listaComponente = new ArrayList();
        
        listaComponente.add(usoProcessador);
        listaComponente.add(usoMemoria);
        listaComponente.add(usoDisco);
        
        for (int i = 0; i < listaComponente.size(); i++) {
            if (listaComponente.get(i) >= 65 && listaComponente.get(i) <= 75) {
                alerta.validaAlerta(usoProcessador, usoMemoria, usoDisco);
            } else if (listaComponente.get(i) >= 75 && listaComponente.get(i) <= 85) {
                alerta.validaAlerta(usoProcessador, usoMemoria, usoDisco);
            } else if (listaComponente.get(i) > 85) {
                alerta.validaAlerta(usoProcessador, usoMemoria, usoDisco);
            }
            
        }
        
    }
    
}
