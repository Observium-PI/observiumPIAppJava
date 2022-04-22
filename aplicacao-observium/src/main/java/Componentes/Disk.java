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
    DiscosGroup grupoDisco = new DiscosGroup();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    
    public Disk(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Integer qtdDiscos() {
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        Integer qtdDiscos = grupoDeDiscos.getQuantidadeDeDiscos();
        
        return qtdDiscos;
    }
    
    public Object totalDisco(Integer disco) {
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        List<List> parametrosDiscos = new ArrayList<>();
        List<Object> totalDiscos = new ArrayList<>();
        
        for (int i = 0; i < qtdDiscos(); i++) {
            List<Discos> discos = new ArrayList<>();
            
            Long totalReduzido = (grupoDeDiscos.getDiscos().get(i).getTamanho()) / 1000000000;
            String stringTotalReduzido = String.valueOf(totalReduzido);
            Integer integerTotalReduzido = Integer.valueOf(stringTotalReduzido);
            
            Discos discoTamanho = new Discos(integerTotalReduzido);
            
            discos.add(discoTamanho);
            parametrosDiscos.add(discos);
            totalDiscos.add(parametrosDiscos.get(i).get(0));
        }
        
        return totalDiscos.get(disco);
    }
    
    public Object disponivelDisco(Integer disco) {
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        List<List> parametrosDiscos = new ArrayList<>();
        List<Object> disponivelDiscos = new ArrayList<>();
        
        for (int i = 0; i < qtdDiscos(); i++) {
            List<Discos> discos = new ArrayList<>();
            
            Long disponivelReduzido = (grupoDeDiscos.getDiscos().get(0).getTamanho());
            String stringDisponivelReduzido = String.valueOf(disponivelReduzido);
            Integer integerDisponivelReduzido = Integer.valueOf(stringDisponivelReduzido);
            
            Discos discoDisponivel = new Discos(integerDisponivelReduzido);
            
            discos.add(discoDisponivel);
            parametrosDiscos.add(discos);
            disponivelDiscos.add(parametrosDiscos.get(i).get(0));
        }
        
        return disponivelDiscos.get(disco);
    }
    
}
