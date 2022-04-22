package Componentes;

public class Discos {
    private Integer parametroDisco;

    public Discos(Integer parametroDisco) {
        this.parametroDisco = parametroDisco;
    }

    public Integer getParametroDisco() {
        return parametroDisco;
    }

    public void setParametroDisco(Integer parametroDisco) {
        this.parametroDisco = parametroDisco;
    }
    
    @Override
    public String toString() {
        return String.format("%d", parametroDisco);
    }
}
