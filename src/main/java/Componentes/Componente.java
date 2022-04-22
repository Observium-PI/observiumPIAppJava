package Componentes;

public class Componente {
    private String tipoComponente;
    private Integer fkComputador;

    public Componente(String tipoComponente, Integer fkComputador) {
        this.tipoComponente = tipoComponente;
        this.fkComputador = fkComputador;
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
