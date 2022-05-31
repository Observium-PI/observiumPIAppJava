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
        List<String> listaInfoUsu = new ArrayList<>();
        
        if (!temUsuario) {
            List<Map<String, Object>> usuEmail = conexao.getConexaoNuvem().queryForList(
                  "select email from Usuario where login = ?", login);
            
            Object email = usuEmail;
            String emailUsu = String.valueOf(email);
            emailUsu = emailUsu.replace("[{email=", "");
            emailUsu = emailUsu.replace("}]", "");
            
            List<Map<String, Object>> usuSetor = conexao.getConexaoNuvem().queryForList(
                  "select setor from Usuario where login = ?", login);
            
            Object setor = usuSetor;
            String setorUsu = String.valueOf(setor);
            setorUsu = setorUsu.replace("[{setor=", "");
            setorUsu = setorUsu.replace("}]", "");
            
            List<Map<String, Object>> usuTipo = conexao.getConexaoNuvem().queryForList(
                  "select tipoUsuario from Usuario where login = ?", login);
            
            Object tipo = usuTipo;
            String tipoUsu = String.valueOf(tipo);
            tipoUsu = tipoUsu.replace("[{tipoUsuario=", "");
            tipoUsu = tipoUsu.replace("}]", "");
            
            List<Map<String, Object>> usuHospital = conexao.getConexaoNuvem().queryForList(
                  "select fkHospital from Usuario where login = ?", login);
            
            Object hospital = usuHospital;
            String fkHospital = String.valueOf(hospital);
            fkHospital = fkHospital.replace("[{fkHospital=", "");
            fkHospital = fkHospital.replace("}]", "");
            
            listaInfoUsu.add(emailUsu);
            listaInfoUsu.add(setorUsu);
            listaInfoUsu.add(tipoUsu);
            listaInfoUsu.add(fkHospital);
        }
        
        return listaInfoUsu;
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
    public Integer buscarIdHospitalNuvem(String login) {
        List<Map<String, Object>> buscaIdHospital = conexao.getConexaoNuvem().queryForList(
                  "select fkHospital from Usuario where login = ?", login);
        
        Object objetoHospital = buscaIdHospital;
        String resultado = String.valueOf(objetoHospital);
        
        resultado = resultado.replace("[{fkHospital=", "");
        resultado = resultado.replace("}]", "");
        Integer hospital = Integer.parseInt(resultado);
        
        return hospital;
    }
    
    public Integer buscarIdHospitalLocal(String login) {
        List<Map<String, Object>> buscaIdHospital = conexao.getConexaoLocal().queryForList(
                  "select fkHospital from Usuario where login = ?", login);
        
        Object objetoHospital = buscaIdHospital;
        String resultado = String.valueOf(objetoHospital);
        
        resultado = resultado.replace("[{fkHospital=", "");
        resultado = resultado.replace("}]", "");
        Integer hospital = Integer.parseInt(resultado);
        
        return hospital;
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
