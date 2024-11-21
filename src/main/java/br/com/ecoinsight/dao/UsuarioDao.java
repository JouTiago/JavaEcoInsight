package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Usuario;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class UsuarioDao implements IUsuarioDao { // Package-private
    private final Connection connection;
    private final String tabela = "T_CADASTRO";

    UsuarioDao() {
        try {
            this.connection = ConnectionFactory.realizarConexao().getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }

    @Override
    public boolean cadastrarUsuario(Usuario usuario) throws Exception {
        String sql = "INSERT INTO " + tabela + " (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getName());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Usuario pesquisarUsuarioPorEmail(String email) throws Exception {
        String sql = "SELECT id, nome, email, senha FROM " + tabela + " WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"), // Adiciona o ID do usuário
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        }
        return null;
    }

    //Salvar token de redefinição de senha
    public boolean salvarTokenRedefinicao(String email, String token) throws Exception {
        String sql = "UPDATE " + tabela + " SET reset_token = ?, token_expiration = SYSDATE + INTERVAL '1' HOUR WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        }
    }

    //Alterar senha
    public boolean alterarSenha(String email, String novaSenha) throws Exception {
        String sql = "UPDATE " + tabela + " SET senha = ?, reset_token = NULL, token_expiration = NULL WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        }
    }

    //Validar token de redefinição
    public boolean validarToken(String email, String token) throws Exception {
        String sql = "SELECT reset_token FROM " + tabela + " WHERE email = ? AND reset_token = ? AND token_expiration > NOW()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, token);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
