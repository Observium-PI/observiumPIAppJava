package Maquina;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import BancoDeDados.ConexaoBancoLocal;

public class MaquinaCrud {
    private JdbcTemplate jdbcTemplate;
    Looca looca = new Looca();
    ConexaoBancoLocal conexao = new ConexaoBancoLocal();
    
    public MaquinaCrud(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexao();
    }
    
    //METODO PARA INCLUIR APENAS UMA MAQUINA
    public void incluir(Maquina novaMaquina) {
        jdbcTemplate.update("insert into Computador (hostName, endMac, fabricante, "
                + "arquitetura, sistemaOperacional, fkHospital) values (?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getFkHospital());
    }
    
    //METODO PARA INCLUIR MAIS DE UMA MAQUINA
    public void incluirMaquinas(List<Maquina> novasMaquinas) {
        for (Maquina novasMaquina : novasMaquinas) {
            jdbcTemplate.update("insert into Computador (endMac, nome, fabricante, "
                + "arquitetura, sistemaOperacional, fkHospital) values (?,?,?,?,?,?)",
            novasMaquina.getHostName(),
            novasMaquina.getEndMac(),
            novasMaquina.getFabricante(),
            novasMaquina.getArquitetura(),
            novasMaquina.getSistemaOperacional(),
            novasMaquina.getFkHospital());
        }
    }
    
    public String buscarHostName() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        
        return hostName;
    }
    
    public String buscarEndMac() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] hardwareAdress = ni.getHardwareAddress();
        
        String[] hexadecimal = new String[hardwareAdress.length];
        for (int i = 0; i < hardwareAdress.length; i++) {
            hexadecimal[i] = String.format("%02X", hardwareAdress[i]);
        }
        String macAdress = String.join("-", hexadecimal);
        
        return macAdress;
    }
    
    public String buscarFabricante() {
        String fabricante = looca.getSistema().getFabricante();
        
        return fabricante;
    }
    
    public Integer buscarArquitetura() {
        Integer arquitetura = looca.getSistema().getArquitetura();
        
        return arquitetura;
    }
    
    public String buscarSO() {
        String sistemaOperacional = looca.getSistema().getSistemaOperacional();
        
        return sistemaOperacional;
    }
    
}
