package Maquina;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Maquina {
    private JdbcTemplate jdbcTemplate;
    
    public Maquina(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate();
    }
    
    private String hostName;
    private String apelidoMaq;
    private String endMac;
    private String fabricante;
    private Integer arquitetura;
    private String sistemaOperacional;
    private String localidade;
    private Integer fkHospital;

    public Maquina(String hostName, String apelidoMaq, String endMac, String fabricante, Integer arquitetura, String sistemaOperacional, String localidade, Integer fkHospital) {
        this.hostName = hostName;
        this.apelidoMaq = apelidoMaq;
        this.endMac = endMac;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.sistemaOperacional = sistemaOperacional;
        this.localidade = localidade;
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

    public String getApelidoMaq() {
        return apelidoMaq;
    }

    public void setApelidoMaq(String apelidoMaq) {
        this.apelidoMaq = apelidoMaq;
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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public Integer getFkHospital() {
        return fkHospital;
    }

    public void setFkHospital(Integer fkHospital) {
        this.fkHospital = fkHospital;
    }
}
