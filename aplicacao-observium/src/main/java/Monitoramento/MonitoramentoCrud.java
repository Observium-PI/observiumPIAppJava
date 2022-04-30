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
    
    //MÉTODO PARA INSERIR O MONITORAMENTO NO BANCO DE DADOS
    public void incluirMonitoramento(Monitoramento novoMonitoramento) {
        jdbcTemplate.update("insert into Monitoramento (fkComponente, dataHora,"
                + "medida, unidadeDeMedida) values (?,?,?,?)",
        novoMonitoramento.getFkComponente(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getMedida(),
        novoMonitoramento.getUnidadeDeMedida());
    }
    
    //MÉTODO PARA BUSCAR O ID DE UM COMPONENTE UTILIZANDO O TIPO DO COMPONENTE
    //E O ENDEREÇO MAC DO COMPUTADOR
    public Integer buscarIdComponente(String tipoComponente, String endMAC) {
        List<Map<String, Object>> buscarId = conexao.getConexao().queryForList(
                "select idComponente from Componente as C join Computador as PC "
              + "on fkComputador = idComputador where C.tipoComponente = ? "
              + "and PC.endMAC = ?", tipoComponente, endMAC);
        
        Object id = buscarId;
        
        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String idComp = String.valueOf(id);
        idComp = idComp.replace("[{idComponente=", "");
        idComp = idComp.replace("}]", "");
        
        Integer idComponente = Integer.valueOf(idComp);
        
        return idComponente;
    }
}
