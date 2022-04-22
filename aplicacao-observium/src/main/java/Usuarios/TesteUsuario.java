package Usuarios;

import Usuarios.UsuarioCrud;
import BancoDeDados.ConexaoBancoLocal;
import org.apache.commons.dbcp2.BasicDataSource;

public class TesteUsuario {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/Observium?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("Dan-auto85");
        
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
