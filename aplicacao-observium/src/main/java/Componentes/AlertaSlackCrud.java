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

    public void resgatarIdMonitoramento(String msg, String hostname, String dataHora,String tipoComponente) {
        List<Map<String, Object>> buscarId = conexao.getConexaoNuvem().queryForList(
                "select top 1 idMonitoramento from monitoramento inner join componente"
                + " on fkComponente = idComponente inner join computador on "
                + "fkComputador = idComputador where hostname = ? and"
                + " tipoComponente like ? order by dataHora desc", hostname,tipoComponente+"%");

        Object id = buscarId;

        //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
        //EM UM NÚMERO INTEIRO
        String idMon = String.valueOf(id);
        idMon = idMon.replace("[{idMonitoramento=", "");
        idMon = idMon.replace("}]", "");

        Integer idMonitoramento = Integer.valueOf(idMon);

        System.out.println(idMonitoramento);

        enviarLogParaBanco(msg, idMonitoramento, dataHora);
    }

    public void enviarLogParaBanco(String msg, Integer fkMonitoramento, String dataHora) {
        System.out.println("\nmsg:" + msg + "\nfk: " + fkMonitoramento);
        jdbcTemplateLocal.update("insert into historico (descricao, fkMonitoramento, dataHora)"
                + " values (?,?,?)", msg, fkMonitoramento, dataHora);

        jdbcTemplateNuvem.update("insert into historico (descricao, fkMonitoramento, dataHora)"
                + " values (?,?,?)", msg, fkMonitoramento, dataHora);
    }
}
