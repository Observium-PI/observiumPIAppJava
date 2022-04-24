package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.discos.Disco;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class Disk {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    
    public Disk(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Integer qtdDiscos() {
        Integer qtdDiscos = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
        
        return qtdDiscos;
    }
    
    public Integer qtdVolumes() {
        Integer qtdVolumes = looca.getGrupoDeDiscos().getQuantidadeDeVolumes();
        
        return qtdVolumes;
    }
    
    public Object totalDisco(Integer disco) {
        DiscosGroup grupoDeVolumes = looca.getGrupoDeDiscos();
        List<List> parametrosDiscos = new ArrayList<>();
//        List<Object> totalVolumes = new ArrayList<>();
        
        for (int i = 0; i < qtdVolumes(); i++) {
            List<Discos> volumes = new ArrayList<>();
            
            Long totalReduzido = (grupoDeVolumes.getVolumes().get(i).getTotal()) / 1000000000;
            String stringTotalReduzido = String.valueOf(totalReduzido);
            Integer integerTotalReduzido = Integer.valueOf(stringTotalReduzido);
            
            if (integerTotalReduzido == 0) {
                System.out.println("disco zerado");
            } else {
                Discos discoTamanho = new Discos(integerTotalReduzido);

                volumes.add(discoTamanho);
                parametrosDiscos.add(volumes);
//                totalVolumes.add(parametrosDiscos);
            }
            
        }
        
        return parametrosDiscos.get(disco);
    }
    
    public Object disponivelDisco(Integer disco) {
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        List<List> parametrosDiscos = new ArrayList<>();
//        List<Object> disponivelVolumes = new ArrayList<>();
        
        for (int i = 0; i < qtdVolumes(); i++) {
            List<Discos> volumes = new ArrayList<>();
            
            Long disponivelReduzido = (grupoDeDiscos.getVolumes().get(i).getDisponivel()) / 1000000000;
            String stringDisponivelReduzido = String.valueOf(disponivelReduzido);
            Integer integerDisponivelReduzido = Integer.valueOf(stringDisponivelReduzido);
            
            if (integerDisponivelReduzido == 0) {
                System.out.println("disco zerado");
            } else {
                Discos discoDisponivel = new Discos(integerDisponivelReduzido);

                volumes.add(discoDisponivel);
                parametrosDiscos.add(volumes);
//                disponivelVolumes.add(parametrosDiscos.get(i).get(0));
            }
            
        }
        
        return parametrosDiscos.get(disco);
    }
    
}
