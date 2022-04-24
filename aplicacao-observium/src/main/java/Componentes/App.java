package Componentes;

import Componentes.Disk;
import Componentes.Cpu;
import Componentes.Memory;
import Maquina.MaquinaCrud;
import org.apache.commons.dbcp2.BasicDataSource;
import Monitoramento.MonitoramentoCrud;
import Monitoramento.Monitoramento;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public void aplicacao() throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        
        Disk disco = new Disk(dataSource);
        Memory memoria = new Memory(dataSource);
        Cpu cpu = new Cpu(dataSource);
        MaquinaCrud maquina = new MaquinaCrud(dataSource);
        
        MonitoramentoCrud monitorar = new MonitoramentoCrud(dataSource);
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String enderecoMac = maquina.buscarEndMac();
        
        //INCLUINDO DADOS SOBRE A CPU
        Integer fkComponenteCpu = monitorar.buscarIdCcomponente("cpu", enderecoMac);
        String dataHoraCpu = dataFormat.format(LocalDateTime.now());
        Double medidaCpu = cpu.usoProcessador();
        String unidadeDeMedidaCpu = "%";
        
        Monitoramento monitoramentoCpu = new Monitoramento(fkComponenteCpu, dataHoraCpu, 
                medidaCpu, unidadeDeMedidaCpu);
        
        monitorar.incluirMonitoramento(monitoramentoCpu);
        
        //INCLUINDO DADOS SOBRE A MEMÓRIA
        Integer fkComponenteMemoria = monitorar.buscarIdCcomponente("memoriaRAM", enderecoMac);
        String dataHoraMemoria = dataFormat.format(LocalDateTime.now());
        Double medidaMemoria = memoria.memoriaEmUso() / 1000000000;
        String unidadeDeMedidaMemoria = "GB";
        
        Monitoramento monitoramentoMemoria = new Monitoramento(fkComponenteMemoria, dataHoraMemoria, 
                medidaMemoria, unidadeDeMedidaMemoria);
        
        monitorar.incluirMonitoramento(monitoramentoMemoria);
        
        //INCLUINDO DADOS SOBRE O DISCO
        for (int i = 0; i < disco.qtdDiscos(); i++) {
            //TRANSFORMANDO OBJECT EM INTEGER
            Object total = disco.totalDisco(i);
            String totalDisk = String.valueOf(total);
            totalDisk = totalDisk.replace("[", "");
            totalDisk = totalDisk.replace("]", "");
            Integer totalDisco = Integer.valueOf(totalDisk);
            
            Object disponivel = disco.disponivelDisco(i);
            String disponivelDisk = String.valueOf(disponivel);
            disponivelDisk = disponivelDisk.replace("[", "");
            disponivelDisk = disponivelDisk.replace("]", "");
            Integer disponivelDisco = Integer.valueOf(disponivelDisk);
            
            if (totalDisco == 0 && disponivelDisco == 0) {
                System.out.println("Disco zerado");
            } else {
                String discoAtual = "disco " + (i + 1);
            
                Integer fkComponenteDisco = monitorar.buscarIdCcomponente(discoAtual, enderecoMac);
                String dataHoraDisco = dataFormat.format(LocalDateTime.now());
                String unidadeDeMedidaDisco = "%";
                
                //CONTA PARA TRANSFORMAR O USO DO DISCO EM PORCENTAGEM
                Integer usoDisk = totalDisco - disponivelDisco;
                usoDisk = usoDisk * 100;
                usoDisk = usoDisk / totalDisco;

                String medida = String.valueOf(usoDisk);
                Double medidaDisco = Double.valueOf(medida);

                Monitoramento monitoramentoDisco = new Monitoramento(fkComponenteDisco, dataHoraDisco, 
                    medidaDisco, unidadeDeMedidaDisco);

                monitorar.incluirMonitoramento(monitoramentoDisco);
            }
            
        }
                
    }
    
}
