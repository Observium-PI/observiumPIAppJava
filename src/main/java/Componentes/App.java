package Componentes;

import Componentes.Disk;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import com.github.britooo.looca.api.core.Looca;

public class App {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
        Looca looca = new Looca();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("aluno");
        dataSource.setPassword("sptech");
        
        Disk disco = new Disk(dataSource);
        Memory memoria = new Memory(dataSource);
        Cpu cpu = new Cpu(dataSource);
        DiscosGroup grupoDisco = new DiscosGroup();
        Looca discos = new Looca();
        
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
