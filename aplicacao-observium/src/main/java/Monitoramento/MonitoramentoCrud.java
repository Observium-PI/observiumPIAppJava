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
    
    //MÉTODO PARA INSERIR O MONITORAMENTO NO BANCO DE DADOS
    public void incluirMonitoramentoNuvem(Monitoramento novoMonitoramento) {
        jdbcTemplateNuvem.update("insert into Monitoramento (fkComponente, dataHora,"
                + "medida, unidadeDeMedida) values (?,?,?,?)",
        novoMonitoramento.getFkComponente(),
        novoMonitoramento.getDataHora(),
        novoMonitoramento.getMedida(),
        novoMonitoramento.getUnidadeDeMedida());
    }
    
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
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
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
        
        Integer idComponente = Integer.valueOf(idComp);
        
        return idComponente;
    }
    
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
        
        Integer idComponente = Integer.valueOf(idComp);
        
        return idComponente;
    }
}
