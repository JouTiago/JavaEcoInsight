package br.com.ecoinsight.dao;

import br.com.ecoinsight.exception.DatabaseException;
import br.com.ecoinsight.exception.ResourceNotFoundException;
import br.com.ecoinsight.model.Usuario;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UsuarioDao implements IUsuarioDao {
    private final Connection connection;
    private final String tabela = "T_CADASTRO";

    UsuarioDao() {
        this.connection = ConnectionFactory.realizarConexao().getConnection();
    }

    @Override
    public boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO " + tabela + " (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao cadastrar o usuário.", e);
        }
    }

    @Override
    public Usuario pesquisarUsuarioPorEmail(String email) {
        String sql = "SELECT id, nome, email, senha FROM " + tabela + " WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            } else {
                throw new ResourceNotFoundException("Usuário com email " + email + " não encontrado.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar o usuário por email.", e);
        }
    }

    public boolean salvarTokenRedefinicao(String email, String token) {
        String sql = "UPDATE " + tabela + " SET reset_token = ?, token_expiration = SYSDATE + " +
                "INTERVAL '1' HOUR WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar o token de redefinição.", e);
        }
    }

    public boolean alterarSenha(String email, String novaSenha) {
        String sql = "UPDATE " + tabela + " SET senha = ?, reset_token = NULL, token_expiration = NULL WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao alterar a senha do usuário.", e);
        }
    }

    public boolean validarToken(String email, String token) {
        String sql = "SELECT reset_token FROM " + tabela + " WHERE email = ? AND reset_token = ? AND " +
                "token_expiration > NOW()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                throw new ResourceNotFoundException("Token inválido ou expirado para o email: " + email);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao validar o token de redefinição.", e);
        }
    }
}
