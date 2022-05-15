package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class Memory {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    Memoria memoria = new Memoria();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    
    public Memory(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Double memoriaTotal() {
        Long memoria = looca.getMemoria().getTotal();
        String memoriaString = String.valueOf(memoria);
        Double memoriaTotal = Double.valueOf(memoriaString);
        
        return memoriaTotal / 1000000000;
    }
    
    public Double memoriaEmUso() {
        Long memoria = looca.getMemoria().getEmUso();
        String memoriaString = String.valueOf(memoria);
        Double memoriaEmUso = Double.valueOf(memoriaString);
        
        return memoriaEmUso / 1000000000;
    }
    
    public Double memoriaDisponivel() {
        Long memoria = looca.getMemoria().getDisponivel();
        String memoriaString = String.valueOf(memoria);
        Double memoriaDisponivel = Double.valueOf(memoriaString);
        
        return memoriaDisponivel / 1000000000;
    }
}
