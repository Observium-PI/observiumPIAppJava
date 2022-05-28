package Componentes;

import com.github.britooo.looca.api.core.Looca;

public class Disco {
    Looca looca = new Looca();
    
    public String buscarNomeDisco() {
        String disk = looca.getGrupoDeDiscos().getDiscos().get(0).getModelo();
        
        return disk;
    }
    
    public Integer qtdDiscos() {
        Integer qtdDiscos = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
        
        return qtdDiscos;
    }
    
    public Integer buscarTotalDisco() {
        Long totalDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000000000;
        String totalConvertido = String.valueOf(totalDisco);
        
        Integer totalDiscoInteiro = Integer.valueOf(totalConvertido);
        
        return totalDiscoInteiro;
    }
    
    public Integer buscarDisponivelDisco() {
        Long disponivelDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000000000;
        String disponivelConvertido = String.valueOf(disponivelDisco);
        
        Integer disponivelDiscoInteiro = Integer.valueOf(disponivelConvertido);
        
        return disponivelDiscoInteiro;
    }
    
}
