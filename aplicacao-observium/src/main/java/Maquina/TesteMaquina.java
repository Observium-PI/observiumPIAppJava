package Maquina;

import Usuarios.UsuarioCrud;
import Maquina.Maquina;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.dbcp2.BasicDataSource;

public class TesteMaquina {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Dan-auto85");
        
        MaquinaCrud Computador = new MaquinaCrud(dataSource);
        UsuarioCrud Usuario = new UsuarioCrud(dataSource);
        
        String endMac = Computador.buscarEndMac();
        String hostName = Computador.buscarHostName();
        String fabricante = Computador.buscarFabricante();
        Integer arquitetura = Computador.buscarArquitetura();
        String sistemaOperacional = Computador.buscarSO();
        
        Maquina maquina = new Maquina(endMac, hostName, fabricante, 
                arquitetura, sistemaOperacional, 3);
        
        Computador.incluir(maquina);

//        Object listaUsuarios = Computador.listarUsuarios().get(0).toString();
//        Object listaSenhas = Computador.listarSenhas().get(0).toString();
//        System.out.println(listaSenhas);
//        System.out.println(listaUsuarios);

    }
    
}
