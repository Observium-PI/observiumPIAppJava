package Maquina;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Maquina {
    
    private JdbcTemplate jdbcTemplate;
    
    public Maquina(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate();
    }
    
    private String hostName;
    private String endMac;
    private String fabricante;
    private Integer arquitetura;
    private String sistemaOperacional;
    private Integer fkHospital;

    public Maquina(String hostName, String endMac, String fabricante, Integer arquitetura, String sistemaOperacional, Integer fkHospital) {
        this.hostName = hostName;
        this.endMac = endMac;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.sistemaOperacional = sistemaOperacional;
        this.fkHospital = fkHospital;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    public String getEndMac() {
        return endMac;
    }

    public void setEndMac(String endMac) {
        this.endMac = endMac;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(Integer arquitetura) {
        this.arquitetura = arquitetura;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Integer getFkHospital() {
        return fkHospital;
    }

    public void setFkHospital(Integer fkHospital) {
        this.fkHospital = fkHospital;
    }
}
