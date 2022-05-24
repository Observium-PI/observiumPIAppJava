package Usuarios;

import Usuarios.UsuarioCrud;
import BancoDeDados.ConexaoBanco;
import org.apache.commons.dbcp2.BasicDataSource;

public class TesteUsuario {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        
        UsuarioCrud usuario = new UsuarioCrud(dataSource);
        
        System.out.println(usuario.inserindoUsuarioNoLocal("MARCOLI"));
    }
}
