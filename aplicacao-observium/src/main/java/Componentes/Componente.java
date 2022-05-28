package Componentes;

public class Componente {
    private String nomeComponente;
    private String tipoComponente;
    private Integer fkComputador;

    public Componente(String nomeComponente, String tipoComponente, Integer fkComputador) {
        this.nomeComponente = nomeComponente;
        this.tipoComponente = tipoComponente;
        this.fkComputador = fkComputador;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }
    
    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }
    
    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Integer getFkComputador() {
        return fkComputador;
    }

    public void setFkComputador(Integer fkComputador) {
        this.fkComputador = fkComputador;
    }

}
