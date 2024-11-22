package br.com.ecoinsight.dao;

import br.com.ecoinsight.exception.DatabaseException;
import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DiagnosticoDao implements IDiagnosticoDao {
    private final Connection connection;
    String tabela = "T_DIAGNOSTICO";

    DiagnosticoDao() {
        this.connection = ConnectionFactory.realizarConexao().getConnection();
    }

    @Override
    public void saveDiagnostic(Diagnostico diagnostico) {
        String sql = "INSERT INTO " + tabela + " (project_id, llm_analysis, sustainability_score, llm_justification) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diagnostico.getProjectId());
            stmt.setString(2, diagnostico.getLlmAnalysis());
            stmt.setDouble(3, diagnostico.getSustainabilityScore());
            stmt.setString(4, diagnostico.getLlmJustification());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao salvar o diagnóstico no banco de dados.", e);
        }
    }

    @Override
    public Diagnostico getDiagnosticByProjectId(int projectId) {
        String sql = "SELECT * FROM " + tabela + " WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Diagnostico(
                        rs.getInt("project_id"),
                        rs.getString("llm_analysis"),
                        rs.getDouble("sustainability_score"),
                        rs.getString("llm_justification")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao recuperar o diagnóstico para o ID" + projectId, e);
        }
        return null;
    }
}
