package Componentes;

import BancoDeDados.ConexaoBanco;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ComponenteCrud {
    private JdbcTemplate jdbcTemplateNuvem;
    private JdbcTemplate jdbcTemplateLocal;
    
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBanco conexao = new ConexaoBanco();
    
    public ComponenteCrud(BasicDataSource dataSource) {
        jdbcTemplateNuvem = conexao.getConexaoNuvem();
        jdbcTemplateLocal = conexao.getConexaoLocal();
    }
    
    //MÉTODO PARA INCLUIR UM COMPONENTE NA TABELA DO BANCO DE DADOS
    public void incluirComponenteNuvem(Componente novoComponente) {
        jdbcTemplateNuvem.update("insert into Componente (nomeComponente, tipoComponente, fkComputador) "
                + "values (?,?,?)",
        novoComponente.getNomeComponente(),
        novoComponente.getTipoComponente(),
        novoComponente.getFkComputador());
    }
    
    public void incluirComponenteLocal(Componente novoComponente) {
          jdbcTemplateLocal.update("insert into Componente (nomeComponente, tipoComponente, fkComputador) "
                + "values (?,?,?)",
        novoComponente.getNomeComponente(),
        novoComponente.getTipoComponente(),
        novoComponente.getFkComputador());
    }
    
}
