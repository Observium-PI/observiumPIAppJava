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
import java.util.Map;

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
    public void incluirNuvem(Maquina novaMaquina) {
        jdbcTemplateNuvem.update("insert into Computador (hostname, apelidoMaquina, endMAC, fabricante, "
                + "arquitetura, sistemaOperacional, localidade, fkHospital) values (?,?,?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getApelidoMaq(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getLocalidade(),
        novaMaquina.getFkHospital());

    }
    
    //MÉTODO PARA INCLUIR APENAS UMA MÁQUINA NO LOCAL
    public void incluirLocal(Maquina novaMaquina){
        jdbcTemplateLocal.update("insert into Computador (hostname, apelidoMaquina, endMAC, fabricante, "
                + "arquitetura, sistemaOperacional, localidade, fkHospital) values (?,?,?,?,?,?,?,?)",
        novaMaquina.getHostName(),
        novaMaquina.getApelidoMaq(),
        novaMaquina.getEndMac(),
        novaMaquina.getFabricante(),
        novaMaquina.getArquitetura(),
        novaMaquina.getSistemaOperacional(),
        novaMaquina.getLocalidade(),
        novaMaquina.getFkHospital());
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
    
    //MÉTODO PARA BUSCAR O ID DO COMPUTADOR NO BANCO NA NUVEM
    public Integer buscarIdComputadorNuvem(String hostname) {
        Integer idComputador = 0;
        
        List<Map<String, Object>> buscarId = conexao.getConexaoNuvem().queryForList(
                "select idComputador from Computador where hostname = ?", hostname);
        
        if (buscarId.size() == 0) {
            return 0;
        } else {
            Object id = buscarId;
        
            //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
            //EM UM NÚMERO INTEIRO
            String idComp = String.valueOf(id);
            idComp = idComp.replace("[{idComputador=", "");
            idComp = idComp.replace("}]", "");

            idComputador = Integer.parseInt(idComp.trim());
        }
        
        return idComputador;
        
    }
    
    //MÉTODO PARA BUSCAR ID DO COMPUTADOR NO BANCO LOCAL
    public Integer buscarIdComputadorLocal(String hostname) {
        List<Map<String, Object>> buscarId = conexao.getConexaoLocal().queryForList(
                "select idComputador from Computador where hostname = ?", hostname);
        
        if (buscarId.size() == 0) {
            return 0;
        } else {
            //RETIRANDO O QUE NÃO É NECESSÁRIO DA BUSCA NO BANCO DE DADOS E TRANSFORMANDO
            //EM UM NÚMERO INTEIRO
            Object id = buscarId;
            
            String idComp = String.valueOf(id);
            idComp = idComp.replace("[{idComputador=", "");
            idComp = idComp.replace("}]", "");

            Integer idComputador = Integer.parseInt(idComp.trim());

            return idComputador;
            
        }
        
    }
    
    public Boolean verificarMaquinaNuvem(String hostname) {
        Boolean hasPc = false;
        
        List<Map<String, Object>> buscarPc = conexao.getConexaoNuvem().queryForList(
                "select idComputador from Computador where hostname = ?", hostname);
        
        if (buscarPc.size() != 0) {
            hasPc = true;
        }
        
        return hasPc;
    }
    
    public Boolean verificarMaquinaLocal(String hostname) {
        Boolean hasPc = false;
        
        List<Map<String, Object>> buscarPc = conexao.getConexaoLocal().queryForList(
                "select idComputador from Computador where hostname = ?", hostname);
        
        if (buscarPc.size() != 0) {
            hasPc = true;
        }
        
        return hasPc;
    }
    
    public String buscarLocalMaqNuvem(String hostname) {
        List<Map<String, Object>> local = conexao.getConexaoNuvem().queryForList(
                "select localidade from Computador where hostname = ?", hostname);
        
        Object localPc = local;
            
        String localidade = String.valueOf(localPc);
        localidade = localidade.replace("[{localidade=", "");
        localidade = localidade.replace("}]", "");

        return localidade;
            
    }
    
    public String buscarLocalMaqLocal(String hostname) {
        List<Map<String, Object>> local = conexao.getConexaoLocal().queryForList(
                "select localidade from Computador where hostname = ?", hostname);
        
        Object localPc = local;
            
        String localidade = String.valueOf(localPc);
        localidade = localidade.replace("[{localidade=", "");
        localidade = localidade.replace("}]", "");

        return localidade;
    }
    
}
