package br.edu.iftm.ppw2.aula1.dao;

import br.edu.iftm.ppw2.aula1.entity.Usuario;
import br.edu.iftm.ppw2.aula1.util.exception.ErroSistemaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private String url = "jdbc:postgresql://localhost:5432/sistema_financeiro";
    private String usuario_banco = "postgres";
    private String senha = "123456";

    private Connection conexao = null;

    private Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                Class.forName("org.postgresql.Driver");
                conexao = DriverManager.getConnection(url, usuario_banco, senha);
            }
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (ClassNotFoundException ex) {
            System.getLogger(UsuarioDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return conexao;
    }

    public void salvar(Usuario usuario) throws ErroSistemaException {
        try {
            String sql;
            PreparedStatement ps;
            if(usuario.getId() == null) {
                sql = "INSERT INTO usuario(nome, email, senha)  VALUES (?, ?, ?)";
                ps  = getConexao().prepareStatement(sql);
            } else {
                sql = "UPDATE usuario set nome = ?, email = ?, senha = ? where id = ?";
                ps  = getConexao().prepareStatement(sql);
                ps.setLong(4, usuario.getId());
            }
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.execute();
            
        } catch (SQLException ex) {
            throw new ErroSistemaException("Erro ao salvar usuário", ex);
        }
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Statement st = getConexao().createStatement();
            ResultSet rs = st.executeQuery("select * from usuario");
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                usuarios.add(u);
            }
        } catch (SQLException ex) {
            System.getLogger(UsuarioDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return usuarios;
    }

}
