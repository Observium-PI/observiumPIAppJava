package Monitoramento;

import BancoDeDados.ConexaoBanco;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import Monitoramento.Monitoramento;
import java.util.List;
import java.util.Map;

public class MonitoramentoCrud {
    private JdbcTemplate jdbcTemplateNuvem;
    private JdbcTemplate jdbcTemplateLocal;
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBanco conexao = new ConexaoBanco();
    
    public MonitoramentoCrud(BasicDataSource dataSource) {
        jdbcTemplateNuvem = conexao.getConexaoNuvem();
        jdbcTemplateLocal = conexao.getConexaoLocal();
    }
    
    //MÉTODO PARA INSERIR O MONITORAMENTO NO BANCO DE DADOS NA NUVEM
    public void incluirMonitoramentoNuvem(Monitoramento novoMonitoramento) {
        jdbcTemplateNuvem.update("insert into Monitoramento (fkComponente, dataHora,"
                + "medida, unidadeDeMedida) values (?,?,?,?)",
        novoMonitoramento.getFkComponente(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getMedida(),
        novoMonitoramento.getUnidadeDeMedida());
    }
    
    // MÉTODO PARA INSERIR O MONITORAMENTO NO BD LOCAL
    public void incluirMonitoramentoLocal(Monitoramento novoMonitoramento){
        jdbcTemplateLocal.update("insert into Monitoramento (fkComponente, dataHora,"
                + "medida, unidadeDeMedida) values (?,?,?,?)",
        novoMonitoramento.getFkComponente(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getMedida(),
        novoMonitoramento.getUnidadeDeMedida());
    }
    
    //MÉTODO PARA BUSCAR O ID DE UM COMPONENTE UTILIZANDO O TIPO DO COMPONENTE
    //E O ENDEREÇO MAC DO COMPUTADOR
    public Integer buscarIdComponenteNuvem(String tipoComponente, String endMAC) {
        List<Map<String, Object>> buscarId = conexao.getConexaoNuvem().queryForList(
                "select idComponente from Componente as C join Computador as PC "
              + "on fkComputador = idComputador where C.tipoComponente = ? "
              + "and PC.endMAC = ?", tipoComponente, endMAC);
        
        Object id = buscarId;
        
        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String idComp = String.valueOf(id);
        idComp = idComp.replace("[{idComponente=", "");
        idComp = idComp.replace("}]", "");
        
        Integer idComponenteN = Integer.parseInt(idComp.trim());
        
        return idComponenteN;
    }
    
    //MÉTODO PARA BUSCAR ID DE COMPONENTE NO BANCO LOCAL
    public Integer buscarIdComponenteLocal(String tipoComponente, String endMAC) {
        List<Map<String, Object>> buscarId = conexao.getConexaoLocal().queryForList(
                "select idComponente from Componente as C join Computador as PC "
              + "on fkComputador = idComputador where C.tipoComponente = ? "
              + "and PC.endMAC = ?", tipoComponente, endMAC);
        
        Object id = buscarId;
        
        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String idComp = String.valueOf(id);
        idComp = idComp.replace("[{idComponente=", "");
        idComp = idComp.replace("}]", "");
        
        Integer idComponenteL = Integer.parseInt(idComp.trim());
        
        return idComponenteL;
    }
}
