package Maquina;

import Usuarios.UsuarioCrud;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.util.Conversor;
import Maquina.Maquina;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.dbcp2.BasicDataSource;
import Componentes.Disco;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TesteMaquina {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        Looca looca = new Looca();
        Conversor convert = new Conversor();
        
        MaquinaCrud Computador = new MaquinaCrud(dataSource);
        UsuarioCrud Usuario = new UsuarioCrud(dataSource);
        
        String endMac = Computador.buscarEndMac();
        String hostName = Computador.buscarHostName();
        String fabricante = Computador.buscarFabricante();
        Integer arquitetura = Computador.buscarArquitetura();
        String sistemaOperacional = Computador.buscarSO();
        
        long sss = looca.getMemoria().getTotal();
        
        convert.formatarBytes(sss);
        
        System.out.println(looca.getProcessador().getNome());
        System.out.println(String.format("memoriaRAM-%s", convert.formatarBytes(sss)));
        System.out.println(looca.getGrupoDeDiscos().getDiscos().get(0).getModelo());
        
        DecimalFormat formatador = new DecimalFormat("0");
        System.out.println(formatador.format(looca.getProcessador().getUso()));
        
        Long memoria = looca.getMemoria().getTotal();
        String memoriaString = String.valueOf(memoria);
        Double memoriaTotal = Double.valueOf(memoriaString);
        
        System.out.println(formatador.format(memoriaTotal / 1000000000));
        
        Long totalDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000000000;
        String totalConvertido = String.valueOf(totalDisco);
        
        Integer totalDiscoInteiro = Integer.valueOf(totalConvertido);
        
        System.out.println(totalDiscoInteiro);
        
        Long disponivelDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000000000;
        String disponivelConvertido = String.valueOf(disponivelDisco);
        
        Integer disponivelDiscoInteiro = Integer.valueOf(disponivelConvertido);
        
        System.out.println(disponivelDiscoInteiro);
        
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dataFormat.format(LocalDateTime.now()));

    }
    
}
