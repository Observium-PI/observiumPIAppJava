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
    public static void main(String[] args) throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Dan-auto85");
        
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
        
        System.out.println("Uso da CPU: " + cpu.usoProcessador());
        System.out.println("Memória em uso: " + memoria.memoriaEmUso());
        System.out.println("Memória disponivel: " + memoria.memoriaDisponivel());
        System.out.println("Memória total: " + memoria.memoriaTotal());
        
//        for (int i = 0; i < disco.qtdDiscos(); i++) {
//            System.out.println("Tamanho do disco " + (i + 1) + ": " + disco.totalDisco(i));
//            System.out.println("Espaço disponivel no disco: " + disco.disponivelDisco(i) + "\n");
//        }
    
//        String arroz = teste.get(2).getPontoDeMontagem();
        
//        System.out.println(arroz);
        
//        for (int i = 0; i < discos.getGrupoDeDiscos().getVolumes().size(); i++) {
//            String teseteSemBarra = teste.get(i).getPontoDeMontagem();
//            Long putz = teste.get(i).getTotal() / 1000000000;
//            Long teste2 = teste.get(i).getDisponivel() / 1000000000;
//            Long conta = (putz - teste2);
//            teseteSemBarra = teseteSemBarra.replace(":\\", "");
//            System.out.println( teseteSemBarra);
//            System.out.println(putz);
//            System.out.println(teste2);
//            System.out.println(conta);
//        }

    }
    
}
