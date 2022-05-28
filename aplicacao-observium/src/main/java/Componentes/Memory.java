package Componentes;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.util.Conversor;
import java.text.DecimalFormat;

public class Memory {
    Looca looca = new Looca();
    Memoria memoria = new Memoria();
    DecimalFormat formatador = new DecimalFormat("0");
    Conversor convert = new Conversor();
    Integer divisor = 1000000000;
    
    public String buscarNomeMemoria() {
        long memoriaTotal = looca.getMemoria().getTotal();
        
        String memory = String.format("memoriaRAM-%s", convert.formatarBytes(memoriaTotal));
        
        return memory;
    }
    
    public Integer memoriaTotal() {
        Long memoria = looca.getMemoria().getTotal();
        String memoriaString = String.valueOf(memoria);
        Double memoriaTotal = Double.valueOf(memoriaString);
        
        String conversaoMemoria = formatador.format(memoriaTotal / divisor);
        
        Integer memoriaTotalInteiro = Integer.valueOf(conversaoMemoria);
        
        return memoriaTotalInteiro;
    }
    
    public Integer memoriaEmUso() {
        Long memoria = looca.getMemoria().getEmUso();
        String memoriaString = String.valueOf(memoria);
        Double memoriaEmUso = Double.valueOf(memoriaString);
        
        String conversaoMemoria = formatador.format(memoriaEmUso / divisor);
        
        Integer memoriaUsoInteiro = Integer.valueOf(conversaoMemoria);
        
        return memoriaUsoInteiro;
    }
    
    public Integer memoriaDisponivel() {
        Long memoria = looca.getMemoria().getDisponivel();
        String memoriaString = String.valueOf(memoria);
        Double memoriaDisponivel = Double.valueOf(memoriaString);
        
        String conversaoMemoria = formatador.format(memoriaDisponivel / divisor);
        
        Integer memoriaDisponivelInteiro = Integer.valueOf(conversaoMemoria);
        
        return memoriaDisponivelInteiro;
    }
    
}
