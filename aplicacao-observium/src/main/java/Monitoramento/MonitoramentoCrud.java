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
        jdbcTemplateNuvem.update("insert into Monitoramento (processador, memoria, disco, "
                + "dataHora, fkComputador) values (?,?,?,?,?)",
        novoMonitoramento.getCpu(),
        novoMonitoramento.getMemoria(),
        novoMonitoramento.getDisco(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getFkComputador());
    }
    
    // MÉTODO PARA INSERIR O MONITORAMENTO NO BD LOCAL
    public void incluirMonitoramentoLocal(Monitoramento novoMonitoramento){
        jdbcTemplateLocal.update("insert into Monitoramento (processador, memoria, disco, "
                + "dataHora, fkComputador) values (?,?,?,?,?)",
        novoMonitoramento.getCpu(),
        novoMonitoramento.getMemoria(),
        novoMonitoramento.getDisco(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getFkComputador());
    }
    
}
