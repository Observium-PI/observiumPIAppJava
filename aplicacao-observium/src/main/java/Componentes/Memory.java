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
    
    public Long memoriaTotal() {
        Long memoriaTotal = memoriaDisponivel() + memoriaEmUso();
        
        return memoriaTotal;
    }
    
    public Long memoriaEmUso() {
        Long memoriaEmUso = looca.getMemoria().getEmUso();
        
        return memoriaEmUso;
    }
    
    public Long memoriaDisponivel() {
        Long memoriaDisponivel = looca.getMemoria().getDisponivel();
        
        return memoriaDisponivel;
    }
}
