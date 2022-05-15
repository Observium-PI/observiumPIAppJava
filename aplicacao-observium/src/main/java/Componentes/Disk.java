package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.util.Conversor;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class Disk {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    Conversor converter = new Conversor();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    
    public Disk(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    //DISCO = HD / SSD DO COMPUTADOR
    //VOLUME = QUALQUER PARTIÇÃO EXISTENTE NO COMPUTADOR 
    
    //MÉTODO PARA BUSCAR A QUANTIDADE DE DISCOS NO COMPUTADOR
    public Integer qtdDiscos() {
        Integer qtdDiscos = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
        
        return qtdDiscos;
    }
    
    //MÉTODO PARA BUSCAR A QUANTIDADE DE VOLUMES NO COMPUTADOR
    public Integer qtdVolumes() {
        Integer qtdVolumes = looca.getGrupoDeDiscos().getQuantidadeDeVolumes();
        
        return qtdVolumes;
    }
    
    //MÉTODO PARA CRIAR UMA LISTA COM O TAMANHO TOTAL DE CADA DISCO DO COMPUTADOR
    public List totalDisco() {
        DiscosGroup grupoDeVolumes = looca.getGrupoDeDiscos();
        List<List> listaTotalDiscos = new ArrayList<>();
        
        for (int i = 0; i < qtdVolumes(); i++) {
            List<Discos> volumes = new ArrayList<>();
            
            Long totalReduzido = (grupoDeVolumes.getVolumes().get(i).getTotal()) / 1000000000;
            String stringTotalReduzido = String.valueOf(totalReduzido);
            Integer integerTotalReduzido = Integer.valueOf(stringTotalReduzido);
            
            if (integerTotalReduzido != 0) {
                Discos discoTamanho = new Discos(integerTotalReduzido);

                volumes.add(discoTamanho);
                listaTotalDiscos.add(volumes);
            }
            
        }
        
        return listaTotalDiscos;
    }
    
    //MÉTODO PARA CRIAR UMA LISTA COM A QUANTIDADE DE ESPAÇO DISPONIVEL DE CADA 
    //DISCO DO COMPUTADOR
    public List disponivelDisco() {
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        List<List> listaDisponivelDiscos = new ArrayList<>();
        
        for (int i = 0; i < qtdVolumes(); i++) {
            List<Discos> volumes = new ArrayList<>();
            
            Long disponivelReduzido = (grupoDeDiscos.getVolumes().get(i).getDisponivel()) / 1000000000;
            String stringDisponivelReduzido = String.valueOf(disponivelReduzido);
            Integer integerDisponivelReduzido = Integer.valueOf(stringDisponivelReduzido);
            
            if (integerDisponivelReduzido != 0) {
                Discos discoDisponivel = new Discos(integerDisponivelReduzido);

                volumes.add(discoDisponivel);
                listaDisponivelDiscos.add(volumes);
            }
            
        }
        
        return listaDisponivelDiscos;
    }
    
}
