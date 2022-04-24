package Usuarios;

import BancoDeDados.ConexaoBancoLocal;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsuarioCrud {
    private JdbcTemplate jdbcTemplate;
    private String nomeUsuario;
    private String loginUsuario;
    
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBancoLocal conexao = new ConexaoBancoLocal();
    
    public UsuarioCrud(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexao();
    }

    public UsuarioCrud(String usuario) {
        this.nomeUsuario = usuario;
    }
    
    public List validarUsuario(String login, String senha) {
        List<Map<String, Object>> buscaUsuario = conexao.getConexao().queryForList(
                  "select count(login) from Usuario where login = ? "
                          + "and senha = ?", login, senha);
        
        return buscaUsuario;
    }
    
    public String buscarNomeUsuario(String usuario) {
        List<Map<String, Object>> nameUsuario = conexao.getConexao().queryForList(
                  "select nome from Usuario where login = ?", usuario);
        
        Object nomeUser = nameUsuario;

        String nome = String.valueOf(nomeUser);
        nome = nome.replace("[{nome=", "");
        nome = nome.replace("}]", "");
        
        setLoginUsuario(usuario);
        
        return nome;
    }
    
    public List buscarIdHospital(String login) {
        List<Map<String, Object>> buscaIdHospital = conexao.getConexao().queryForList(
                  "select fkHospital from Usuario where login = ?", login);
        
        return buscaIdHospital;
    }

    public String getUsuario() {
        return nomeUsuario;
    }

    public void setUsuario(String usuario) {
        this.nomeUsuario = usuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
    
    @Override
    public String toString() {
        return String.format("%s", nomeUsuario);
    }
}
