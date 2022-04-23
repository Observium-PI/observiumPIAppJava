package Monitoramento;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Monitoramento {
    private JdbcTemplate jdbcTemplate;
    
    public Monitoramento(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate();
    }
    
    private Integer fkComponente;
    private String dataHora;
    private Double medida;
    private String unidadeDeMedida;

    public Monitoramento(Integer fkComponente, String dataHora, Double medida, String unidadeDeMedida) {
        this.fkComponente = fkComponente;
        this.dataHora = dataHora;
        this.medida = medida;
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Double getMedida() {
        return medida;
    }

    public void setMedida(Double medida) {
        this.medida = medida;
    }

    public String getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }
    
}
