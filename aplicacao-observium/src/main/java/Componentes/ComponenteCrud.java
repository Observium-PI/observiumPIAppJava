package Componentes;

import BancoDeDados.ConexaoBanco;
import com.github.britooo.looca.api.core.Looca;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import java.util.List;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.SystemInfo;
import java.util.Map;

public class ComponenteCrud {
    private JdbcTemplate jdbcTemplateNuvem;
    private JdbcTemplate jdbcTemplateLocal;
    Looca looca = new Looca();
    BasicDataSource dataSource = new BasicDataSource();
    HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
    ConexaoBanco conexao = new ConexaoBanco();
    
    public ComponenteCrud(BasicDataSource dataSource) {
        jdbcTemplateNuvem = conexao.getConexaoNuvem();
        jdbcTemplateLocal = conexao.getConexaoLocal();
    }
    
    //MÉTODO PARA INCLUIR UM COMPONENTE NA TABELA DO BANCO DE DADOS
    public void incluirComponente(Componente novoComponente) {
        jdbcTemplateNuvem.update("insert into Componente (tipoComponente, fkComputador) "
                + "values (?,?)",
        novoComponente.getTipoComponente(),
        novoComponente.getFkComputador());
        
        /*jdbcTemplateLocal.update("insert into Componente (tipoComponente, fkComputador) "
                + "values (?,?)",
        novoComponente.getTipoComponente(),
        novoComponente.getFkComputador());*/
    }
    
    //MÉTODO PARA BUSCAR O ID DO COMPUTADOR A PARTIR DO HOSTNAME DA MÁQUINA
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public List buscarIdComputador(String hostName) {
        List<Map<String, Object>> buscaIdComputador = conexao.getConexaoNuvem().queryForList(
                  "select idComputador from Computador where hostName = ?", hostName);
        
        return buscaIdComputador;
    }
    
    //MÉTODO PARA VERIFICAÇÃO DE CPU
    public String buscarCpuComponente() {
        String cpu = null;
        Integer qtdProcessador = hardware.getProcessor().getPhysicalPackageCount();
        
        if (qtdProcessador != 0) {
            cpu = "cpu";
        }
        
        return cpu;
    }
    
    //MÉTODO PARA VERIFICAÇÃO DE MEMÓRIA RAM
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
