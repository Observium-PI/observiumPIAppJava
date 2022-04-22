package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.britooo.looca.api.group.processos.ProcessosGroup;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class Cpu {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    Processador processador = new Processador();
    ProcessosGroup processos = new ProcessosGroup();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    
    public Cpu(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Integer qtdProcessador() {
        Integer qtdProcessador = hardware.getProcessor().getPhysicalPackageCount();
        
        return qtdProcessador;
    }
    
    public Double usoProcessador() {
        Double usoProcessador = looca.getProcessador().getUso();
        
        return usoProcessador;
    }
    
    public Integer cpuProcessos() {
        Integer cpuProcessos = looca.getGrupoDeProcessos().getTotalProcessos();
        
        return cpuProcessos;
    }
}
