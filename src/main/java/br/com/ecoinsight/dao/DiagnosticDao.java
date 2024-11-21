package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Diagnostic;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class DiagnosticDao implements IDiagnosticDao { // Package-private
    private final Connection connection;
    String tabela = "T_DIAGNOSTICO";

    DiagnosticDao() {
        try {
            this.connection = ConnectionFactory.realizarConexao().getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }

    @Override
    public void saveDiagnostic(Diagnostic diagnostic) throws Exception {
        String sql = "INSERT INTO " + tabela + " (project_id, llm_analysis, sustainability_score, llm_justification) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, diagnostic.getProjectId());
            stmt.setString(2, diagnostic.getLlmAnalysis());
            stmt.setDouble(3, diagnostic.getSustainabilityScore());
            stmt.setString(4, diagnostic.getLlmJustification());
            stmt.executeUpdate();
        }
    }

    @Override
    public Diagnostic getDiagnosticByProjectId(int projectId) throws Exception {
        String sql = "SELECT * FROM " + tabela + " WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Diagnostic(
                        rs.getInt("project_id"),
                        rs.getString("llm_analysis"),
                        rs.getDouble("sustainability_score"),
                        rs.getString("llm_justification")
                );
            }
        }
        return null;
    }
}
