package Monitoramento;

import BancoDeDados.ConexaoBancoLocal;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import Monitoramento.Monitoramento;
import java.util.List;
import java.util.Map;

public class MonitoramentoCrud {
    private JdbcTemplate jdbcTemplate;
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBancoLocal conexao = new ConexaoBancoLocal();
    
    public MonitoramentoCrud(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexao();
    }
    
    public void incluirMonitoramento(Monitoramento novoMonitoramento) {
        jdbcTemplate.update("insert into Monitoramento (fkComponente, dataHora,"
                + "medida, unidadeDeMedida) values (?,?,?,?)",
        novoMonitoramento.getFkComponente(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getMedida(),
        novoMonitoramento.getUnidadeDeMedida());
    }
    
    public Integer buscarIdCcomponente(String tipoComponente, String endMAC) {
        List<Map<String, Object>> buscarId = conexao.getConexao().queryForList(
                "select idComponente from Componente as C join Computador as PC "
              + "on fkComputador = idComputador where C.tipoComponente = ? "
              + "and PC.endMAC = ?", tipoComponente, endMAC);
        
        Object id = buscarId;

        String idComp = String.valueOf(id);
        idComp = idComp.replace("[{idComponente=", "");
        idComp = idComp.replace("}]", "");
        
        Integer idComponente = Integer.valueOf(idComp);
        
        return idComponente;
    }
}
