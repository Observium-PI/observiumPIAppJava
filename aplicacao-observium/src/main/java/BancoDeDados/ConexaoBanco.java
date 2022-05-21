package BancoDeDados;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoBanco {
    private JdbcTemplate conexaoNuvem; //CONEXÃO AZURE
    private JdbcTemplate conexaoLocal; //CONEXÃO MYSQL

    public ConexaoBanco() {
        BasicDataSource dev = new BasicDataSource();
        dev.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dev.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dev.setUsername("root");
        dev.setPassword("observium123");
        
        BasicDataSource prod = new BasicDataSource();
        prod.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        prod.setUrl("jdbc:sqlserver://bdobservium.database.windows.net;"
                + "database=bd-observium;encrypt=true;trustCertificate=false;"
                + "hostNameInCertificate=*.database.windows.net");
        prod.setUsername("rootObservium");
        prod.setPassword("2ads$grupo6");
        
        conexaoNuvem = new JdbcTemplate(prod);
        conexaoLocal = new JdbcTemplate(dev);
    }

    public JdbcTemplate getConexaoNuvem() {
        return conexaoNuvem;
    }
    
    public JdbcTemplate getConexaoLocal() {
        return conexaoLocal;
    }
}
