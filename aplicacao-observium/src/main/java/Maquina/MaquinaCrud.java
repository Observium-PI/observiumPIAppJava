package Maquina;

import com.github.britooo.looca.api.core.Looca;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import BancoDeDados.ConexaoBanco;

public class MaquinaCrud {
    private JdbcTemplate jdbcTemplateNuvem;
    private JdbcTemplate jdbcTemplateLocal;
    Looca looca = new Looca();
    ConexaoBanco conexao = new ConexaoBanco();
    
    public MaquinaCrud(BasicDataSource dataSource) {
        jdbcTemplateNuvem = conexao.getConexaoNuvem();
        jdbcTemplateLocal = conexao.getConexaoLocal();
    }
    
    //METODO PARA INCLUIR APENAS UMA MAQUINA
    public void incluir(Maquina novaMaquina) {
        jdbcTemplateLocal.update("insert into Computador (hostName, endMac, fabricante, "
                + "arquitetura, sistemaOperacional, localidade, fkHospital) values (?,?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getLocalidade(),
        novaMaquina.getFkHospital());
        
        jdbcTemplateNuvem.update("insert into Computador (hostName, endMac, fabricante, "
                + "arquitetura, sistemaOperacional, localidade, fkHospital) values (?,?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getLocalidade(),
        novaMaquina.getFkHospital());
        
        /*jdbcTemplateNuvem.update("insert into Computador (hostName, endMac, fabricante, "
                + "arquitetura, sistemaOperacional, localidade, fkHospital) values (?,?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getLocalidade(),
        novaMaquina.getFkHospital());*/
    }
    
    //MÉTODO PARA BUSCAR O HOSTNAME DA MÁQUINA
    public String buscarHostName() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        
        return hostName;
    }
    
    //MÉTODO PARA BUSCAR O ENDEREÇO MAC DA MÁQUINA
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
    
    //MÉTODO PARA BUSCAR O FABRICANTE DA MÁQUINA
    public String buscarFabricante() {
        String fabricante = looca.getSistema().getFabricante();
        
        return fabricante;
    }
    
    //MÉTODO PARA BUSCAR A ARQUITETURA DA MÁQUINA
    public Integer buscarArquitetura() {
        Integer arquitetura = looca.getSistema().getArquitetura();
        
        return arquitetura;
    }
    
    //MÉTODO PARA BUSCAR O SISTEMA OPERACIONAL DA MÁQUINA
    public String buscarSO() {
        String sistemaOperacional = looca.getSistema().getSistemaOperacional();
        
        return sistemaOperacional;
    }
    
}
