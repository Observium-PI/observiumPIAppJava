package Maquina;

import Usuarios.UsuarioCrud;
import com.github.britooo.looca.api.core.Looca;
import Maquina.Maquina;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.dbcp2.BasicDataSource;
import Componentes.Disk;

public class TesteMaquina {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        BasicDataSource dataSource = new BasicDataSource();
        Looca looca = new Looca();
        
        MaquinaCrud Computador = new MaquinaCrud(dataSource);
        UsuarioCrud Usuario = new UsuarioCrud(dataSource);
        
        String endMac = Computador.buscarEndMac();
        String hostName = Computador.buscarHostName();
        String fabricante = Computador.buscarFabricante();
        Integer arquitetura = Computador.buscarArquitetura();
        String sistemaOperacional = Computador.buscarSO();
        
        for (int i = 0; i < looca.getGrupoDeDiscos().getQuantidadeDeVolumes(); i++) {
            System.out.println(looca.getGrupoDeDiscos().getVolumes().get(i).getUUID().codePointAt(0));
        }
        
        /*Maquina maquina = new Maquina(endMac, hostName, fabricante, 
                arquitetura, sistemaOperacional, 3);
        
        Computador.incluir(maquina);*/

//        Object listaUsuarios = Computador.listarUsuarios().get(0).toString();
//        Object listaSenhas = Computador.listarSenhas().get(0).toString();
//        System.out.println(listaSenhas);
//        System.out.println(listaUsuarios);

        

    }
    
}
