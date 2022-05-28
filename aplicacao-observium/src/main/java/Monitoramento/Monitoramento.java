package Monitoramento;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import BancoDeDados.ConexaoBanco;

public class Monitoramento {
    private JdbcTemplate jdbcTemplate;
    ConexaoBanco conexao = new ConexaoBanco();
    
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public Monitoramento(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexaoLocal();
    }
    
    private Integer cpu;
    private Integer memoria;
    private Integer disco;
    private String dataHora;
    private Integer fkComputador;

    public Monitoramento(Integer cpu, Integer memoria, Integer disco, String dataHora, Integer fkComputador) {
        this.cpu = cpu;
        this.memoria = memoria;
        this.disco = disco;
        this.dataHora = dataHora;
        this.fkComputador = fkComputador;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getMemoria() {
        return memoria;
    }

    public void setMemoria(Integer memoria) {
        this.memoria = memoria;
    }

    public Integer getDisco() {
        return disco;
    }

    public void setDisco(Integer disco) {
        this.disco = disco;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getFkComputador() {
        return fkComputador;
    }

    public void setFkComputador(Integer fkComputador) {
        this.fkComputador = fkComputador;
    }
    
}
