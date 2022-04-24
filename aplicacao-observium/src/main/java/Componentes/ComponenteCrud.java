package Componentes;

import BancoDeDados.ConexaoBancoLocal;
import com.github.britooo.looca.api.core.Looca;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import java.util.ArrayList;
import java.util.List;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.SystemInfo;
import Maquina.Maquina;
import java.util.Map;

public class ComponenteCrud {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    BasicDataSource dataSource = new BasicDataSource();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    ConexaoBancoLocal conexao = new ConexaoBancoLocal();
    
    public ComponenteCrud(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexao();
    }
    
    public void incluirComponente(Componente novoComponente) {
        jdbcTemplate.update("insert into Componente (tipoComponente, fkComputador) "
                + "values (?,?)",
        novoComponente.getTipoComponente(),
        novoComponente.getFkComputador());
    }
    
    public List buscarIdComputador(String hostName) {
        List<Map<String, Object>> buscaIdComputador = conexao.getConexao().queryForList(
                  "select idComputador from Computador where hostName = ?", hostName);
        
        return buscaIdComputador;
    }
    
    public String buscarCpuComponente() {
        String cpu = null;
        Integer qtdProcessador = hardware.getProcessor().getPhysicalPackageCount();
        
        if (qtdProcessador != 0) {
            cpu = "cpu";
        }
        
        return cpu;
    }
    
    public String buscarMemoriaComponente() {
        String memory = null;
        
        Long qtdMemoria = hardware.getMemory().getAvailable();
        
        if (qtdMemoria != 0) {
            memory = "memoriaRAM";
        }
        
        return memory;
    }
    
    public Integer buscarVolumesComponente() {
        Integer disk = 0;
        
        DiscosGroup grupoDeDiscos = looca.getGrupoDeDiscos();
        Integer qtdDiscos = grupoDeDiscos.getQuantidadeDeDiscos();
        
        if (qtdDiscos > 0) {
            disk = qtdDiscos;
        }
        
        return disk;
    }
    
}
