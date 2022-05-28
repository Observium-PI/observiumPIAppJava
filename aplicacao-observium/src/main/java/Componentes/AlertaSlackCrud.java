package Componentes;

import BancoDeDados.ConexaoBanco;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class AlertaSlackCrud {

    private JdbcTemplate jdbcTemplateNuvem;
    private JdbcTemplate jdbcTemplateLocal;
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBanco conexao = new ConexaoBanco();

    public AlertaSlackCrud(BasicDataSource dataSource) {
        jdbcTemplateNuvem = conexao.getConexaoNuvem();
        jdbcTemplateLocal = conexao.getConexaoLocal();
    }

    public void resgatarIdMonitoramento(String msg, String hostname, String dataHora) {
        List<Map<String, Object>> buscarId = conexao.getConexaoLocal().queryForList(
                "select idMonitoramento from Monitoramento inner join Computador on fkComputador = idComputador "
              + "where hostName = ? order by idMonitoramento desc limit 1;", hostname);

        Object id = buscarId;

        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String idMon = String.valueOf(id);
        idMon = idMon.replace("[{idMonitoramento=", "");
        idMon = idMon.replace("}]", "");

        Integer idMonitoramento = Integer.valueOf(idMon);

        System.out.println(idMonitoramento);

        enviarLogParaBanco(msg, idMonitoramento);
    }

    public void enviarLogParaBanco(String msg, Integer fkMonitoramento) {
        Integer qtdAlerta = verificarQtdAlerta(msg);
        
        if (qtdAlerta >= 2) {
            System.out.println("Envio de alerta já foi realizado");
        } else {
            System.out.println("\nmsg:" + msg + "\nfk: " + fkMonitoramento);
            jdbcTemplateLocal.update("insert into Historico (descricao, fkMonitoramento)"
                + " values (?,?)", msg, fkMonitoramento);

            /*jdbcTemplateNuvem.update("insert into historico (descricao, fkMonitoramento)"
                + " values (?,?)", msg, fkMonitoramento);*/
        }
        
    }
    
    public Integer verificarQtdAlerta(String msg) {
        List<Map<String, Object>> buscarQtdAlerta = conexao.getConexaoLocal().queryForList(
                "select count(descricao) from Historico where descricao = ?", msg);
        
        Object qtdAlerta = buscarQtdAlerta;

        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String qtd = String.valueOf(qtdAlerta);
        qtd = qtd.replace("[{count(descricao)=", "");
        qtd = qtd.replace("}]", "");
        
        Integer qtdAlertaInteiro = Integer.valueOf(qtd);
        
        return qtdAlertaInteiro;
        
    }
    
}
