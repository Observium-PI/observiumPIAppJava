package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
import java.text.DecimalFormat;

public class Cpu {
    Looca looca = new Looca();
    Processador processador = new Processador();    
    DecimalFormat formatador = new DecimalFormat("0");
    
    public String buscarNomeCpu() {
        String cpu = looca.getProcessador().getNome();
        
        return cpu;
    }
    
    public Integer usoProcessador() {
        Double usoProcessador = looca.getProcessador().getUso();
        
        String usoConvertido = formatador.format(usoProcessador);
        
        Integer usoProcessadorInteiro = Integer.valueOf(usoConvertido);
        
        return usoProcessadorInteiro;
    }
    
}
