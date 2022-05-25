package Componentes;

import Maquina.MaquinaCrud;
import org.apache.commons.dbcp2.BasicDataSource;
import Monitoramento.MonitoramentoCrud;
import Monitoramento.Monitoramento;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App {
    public void aplicacao() throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        
        //INSTÂNCIANDO AS CLASSES
        Disk disco = new Disk(dataSource);
        Memory memoria = new Memory(dataSource);
        Cpu cpu = new Cpu(dataSource);
        MaquinaCrud maquina = new MaquinaCrud(dataSource);
        MonitoramentoCrud monitorar = new MonitoramentoCrud(dataSource);
        
        //FORMATANDO A DATA E HORA PARA GUARDAR NO BANCO DE FORMA CORRETA
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
        //BUSCANDO O ENDEREÇO MAC DA MÁQUINA ATUAL PARA UTILIZAR NOS SELECTS
        String enderecoMac = maquina.buscarEndMac();
        
        //BUSCANDO DADOS SOBRE A CPU
        Integer fkComponenteCpuNuvem = monitorar.buscarIdComponenteNuvem("cpu", enderecoMac);
        Integer fkComponenteCpuLocal = monitorar.buscarIdComponenteLocal("cpu", enderecoMac);
        
        String dataHoraCpu = dataFormat.format(LocalDateTime.now());
        Double medidaCpu = cpu.usoProcessador();
        String unidadeDeMedidaCpu = "%";
        
        //INCLUINDO A DATA E HORA DO MONITORAMENTO, A PORCENTAGEM DE USO DA CPU 
        //E SUA UNIDADE DE MEDIDA
        Monitoramento monitoramentoCpuNuvem = new Monitoramento(fkComponenteCpuNuvem, dataHoraCpu, 
                medidaCpu, unidadeDeMedidaCpu);
        
        Monitoramento monitoramentoCpuLocal = new Monitoramento(fkComponenteCpuLocal, dataHoraCpu, 
                medidaCpu, unidadeDeMedidaCpu);
        
        monitorar.incluirMonitoramentoNuvem(monitoramentoCpuNuvem);
        monitorar.incluirMonitoramentoLocal(monitoramentoCpuLocal);
        
        //BUSCANDO DADOS SOBRE A MEMÓRIA
        Integer fkComponenteMemoriaNuvem = monitorar.buscarIdComponenteNuvem("memoriaRAM", enderecoMac);
        Integer fkComponenteMemoriaLocal = monitorar.buscarIdComponenteLocal("memoriaRAM", enderecoMac);
        
        String dataHoraMemoria = dataFormat.format(LocalDateTime.now());
        Double memoriaTotal = memoria.memoriaTotal();
        Double memoriaUsoAtual = memoria.memoriaEmUso();
        
        Double medidaMemoria = (memoriaUsoAtual * 100) / memoriaTotal;
        
        String unidadeDeMedidaMemoria = "%";
        
        //INCLUINDO A DATA E HORA DO MONITORAMENTO, O USO DA MEMÓRIA RAM E SUA
        //UNIDADE DE MEDIDA
        Monitoramento monitoramentoMemoriaNuvem = new Monitoramento(fkComponenteMemoriaNuvem, dataHoraMemoria, 
                medidaMemoria, unidadeDeMedidaMemoria);
        
        Monitoramento monitoramentoMemoriaLocal = new Monitoramento(fkComponenteMemoriaLocal, dataHoraMemoria, 
                medidaMemoria, unidadeDeMedidaMemoria);
        
        monitorar.incluirMonitoramentoNuvem(monitoramentoMemoriaNuvem);
        monitorar.incluirMonitoramentoLocal(monitoramentoMemoriaLocal);
        
        //LISTANDO DADOS SOBRE OS DISCOS
        /*List<Object> listaTotalDisco = disco.totalDisco();
        List<Object> listaDisponivelDisco = disco.disponivelDisco();
        
        for (int i = 0; i < listaTotalDisco.size(); i++) {
            //TRANSFORMANDO A LISTA DE OBJETOS EM UMA STRING, RETIRANDO O QUE NÃO
            //É NECESSÁRIO E DEPOIS CONVERTENDO PARA UM NÚMERO INTEIRO
            String totalDisk = String.valueOf(listaTotalDisco.get(i));
            totalDisk = totalDisk.replace("[", "");
            totalDisk = totalDisk.replace("]", "");
            Integer totalDisco = Integer.valueOf(totalDisk);
            
            String disponivelDisk = String.valueOf(listaDisponivelDisco.get(i));
            disponivelDisk = disponivelDisk.replace("[", "");
            disponivelDisk = disponivelDisk.replace("]", "");
            Integer disponivelDisco = Integer.valueOf(disponivelDisk);
            
            if (totalDisco != 0 && disponivelDisco != 0) {
                //BUSCANDO DADOS SOBRE O DISCO ATUAL DO 'FOR'
                String discoAtual = "disco " + (i + 1);
                Integer fkComponenteDiscoNuvem = monitorar.buscarIdComponenteNuvem(discoAtual, enderecoMac);
                Integer fkComponenteDiscoLocal = monitorar.buscarIdComponenteLocal(discoAtual, enderecoMac);
                String dataHoraDisco = dataFormat.format(LocalDateTime.now());
                String unidadeDeMedidaDisco = "%";
                
                //CONTA PARA TRANSFORMAR O USO DO DISCO ATUAL DO 'FOR' EM PORCENTAGEM
                Integer usoDisk = totalDisco - disponivelDisco;
                usoDisk = usoDisk * 100;
                usoDisk = usoDisk / totalDisco;
                
                String medida = String.valueOf(usoDisk);
                Double medidaDisco = Double.valueOf(medida);
                
                //INCLUINDO A DATA E HORA DO MONITORAMENTO, A PORCENTAGEM DE USO DO DISCO 
                //ATUAL DO 'FOR' E SUA UNIDADE DE MEDIDA
                Monitoramento monitoramentoDiscoNuvem = new Monitoramento(fkComponenteDiscoNuvem, dataHoraDisco, 
                    medidaDisco, unidadeDeMedidaDisco);
                Monitoramento monitoramentoDiscoLocal = new Monitoramento(fkComponenteDiscoLocal, dataHoraDisco, 
                    medidaDisco, unidadeDeMedidaDisco);
                
                monitorar.incluirMonitoramentoNuvem(monitoramentoDiscoNuvem);
                monitorar.incluirMonitoramentoLocal(monitoramentoDiscoLocal);
                
            }
            
        }*/
                
    }
    
}
