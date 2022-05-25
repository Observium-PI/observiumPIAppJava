package Usuarios;

import BancoDeDados.ConexaoBanco;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsuarioCrud {
    private JdbcTemplate jdbcTemplate;
    private String nomeUsuario;
    private String loginUsuario;
    
    BasicDataSource dataSource = new BasicDataSource();
    ConexaoBanco conexao = new ConexaoBanco();
    
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public UsuarioCrud(BasicDataSource dataSource) {
        jdbcTemplate = conexao.getConexaoNuvem();
    }

    public UsuarioCrud(String usuario) {
        this.nomeUsuario = usuario;
    }
    
    //MÉTODO PARA VALIDAR O LOGIN DO USUÁRIO
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public List validarUsuario(String login, String senha) {
        List<Map<String, Object>> buscaUsuario = conexao.getConexaoNuvem().queryForList(
                  "select count(login) from Usuario where login = ? "
                          + "and senha = ?", login, senha);
        
        return buscaUsuario;
    }
    
    public List inserindoUsuarioNoLocal(String login){
        Boolean temUsuario = hasUsuario(login);
        List<String> lista = new ArrayList<>();
        
        if (!temUsuario) {
            List<Map<String, Object>> buscaId = conexao.getConexaoNuvem().queryForList(
                  "select idUsuario from Usuario where login = ?", login);
        
            String idUsuario = String.valueOf(buscaId);
            idUsuario = idUsuario.replace("[{idUsuario=", "");
            idUsuario = idUsuario.replace("}]", "");
            lista.add(idUsuario);

            List<Map<String, Object>> buscaNome = conexao.getConexaoNuvem().queryForList(
                      "select nome from Usuario where login = ?", login);

            String nomeUsuario = String.valueOf(buscaNome);
            nomeUsuario = nomeUsuario.replace("[{nome=", "");
            nomeUsuario = nomeUsuario.replace("}]", "");
            lista.add(nomeUsuario);

            List<Map<String, Object>> buscaEmail = conexao.getConexaoNuvem().queryForList(
                      "select email from Usuario where login = ?", login);

            String emailUsuario = String.valueOf(buscaEmail);
            emailUsuario = emailUsuario.replace("[{email=", "");
            emailUsuario = emailUsuario.replace("}]", "");
            lista.add(emailUsuario);

            List<Map<String, Object>> buscaSetor = conexao.getConexaoNuvem().queryForList(
                      "select setor from Usuario where login = ?", login);

            String setorUsuario = String.valueOf(buscaSetor);
            setorUsuario = setorUsuario.replace("[{setor=", "");
            setorUsuario = setorUsuario.replace("}]", "");
            lista.add(setorUsuario);

            List<Map<String, Object>> buscaTipoUsuario = conexao.getConexaoNuvem().queryForList(
                      "select tipoUsuario from Usuario where login = ?", login);

            String tipoUsuario = String.valueOf(buscaTipoUsuario);
            tipoUsuario = tipoUsuario.replace("[{tipoUsuario=", "");
            tipoUsuario = tipoUsuario.replace("}]", "");
            lista.add(tipoUsuario);

            List<Map<String, Object>> buscaFkHospital = conexao.getConexaoNuvem().queryForList(
                      "select fkHospital from Usuario where login = ?", login);

            String fkHospital = String.valueOf(buscaFkHospital);
            fkHospital = fkHospital.replace("[{fkHospital=", "");
            fkHospital = fkHospital.replace("}]", "");
            lista.add(fkHospital);
        }
        
        return lista;
    }
    
    public Boolean hasUsuario(String nomeUsuario) {
        Boolean hasNome = false;
        
        try {
            List<Map<String, Object>> nameUsuario = conexao.getConexaoLocal().queryForList(
                      "select login from Usuario where login = ?", nomeUsuario);
            
            if (!nameUsuario.isEmpty()) {
                hasNome = true;
            }
        } 
        catch (org.springframework.jdbc.CannotGetJdbcConnectionException e)
        {
            hasNome = false;
        }
        
        return hasNome;
    }
    
    //MÉTODO PARA BUSCAR O NOME DO USUÁRIO LOGADO A PARTIR DO CÓDIGO DE LOGIN
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public String buscarNomeUsuario(String usuario) {
        List<Map<String, Object>> nameUsuario = conexao.getConexaoNuvem().queryForList(
                  "select nome from Usuario where login = ?", usuario);
        
        Object nomeUser = nameUsuario;

        String nome = String.valueOf(nomeUser);
        nome = nome.replace("[{nome=", "");
        nome = nome.replace("}]", "");
        
        setLoginUsuario(usuario);
        
        return nome;
    }
    
    //MÉTODO PARA BUSCAR O ID DO HOSPITAL DO USUÁRIO LOGADO
    //LEMBRAR DE MUDAR PARA 'conexao.getConexaoNuvem'
    public List buscarIdHospitalNuvem(String login) {
        List<Map<String, Object>> buscaIdHospital = conexao.getConexaoNuvem().queryForList(
                  "select fkHospital from Usuario where login = ?", login);
        
        return buscaIdHospital;
    }
    
    public List buscarIdHospitalLocal(String login) {
        List<Map<String, Object>> buscaIdHospital = conexao.getConexaoLocal().queryForList(
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
