package BancoDeDados;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoBancoLocal {
    private JdbcTemplate conexao;

    public ConexaoBancoLocal() {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Dan-auto85");
        
        conexao = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexao() {
        return conexao;
    }
}
