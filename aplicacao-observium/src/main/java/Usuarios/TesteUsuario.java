package Usuarios;

import Usuarios.UsuarioCrud;
import BancoDeDados.ConexaoBancoLocal;
import org.apache.commons.dbcp2.BasicDataSource;

public class TesteUsuario {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        
        UsuarioCrud usuario = new UsuarioCrud(dataSource);
        
        String login1 = usuario.validarUsuario("MARCOLI", "m123o").toString();
        String login2 = usuario.validarUsuario("ROBEMOR", "m123o").toString();
        
        login1 = login1.replace("[{count(login)=", "");
        login1 = login1.replace("}]", "");
        
        login2 = login2.replace("[{count(login)=", "");
        login2 = login2.replace("}]", "");
        
        System.out.println(login1);
        System.out.println(login2);
    }
}
