package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.DiagnosticResponses;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ProjetoDao implements IProjetoDao { // Package-private
    private final Connection connection;
    String tabela = "T_PROJETOS";

    ProjetoDao() {
        try {
            this.connection = ConnectionFactory.realizarConexao().getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }

    @Override
    public int cadastrarProjeto(Projeto projeto, int userId) throws Exception {
        String sql = "INSERT INTO " + tabela + " (description, location, estimated_budget, planned_energy_types, " +
                "environmental_impact_knowledge, environmental_policies, performance_measures, risk_assessment, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, projeto.getDescription());
            stmt.setString(2, projeto.getLocation());
            stmt.setInt(3, projeto.getEstimatedBudget());
            stmt.setString(4, String.join(",", projeto.getPlannedEnergyTypes()));
            DiagnosticResponses responses = projeto.getDiagnosticResponses();
            stmt.setInt(5, responses.getEnvironmentalImpactKnowledge());
            stmt.setInt(6, responses.getEnvironmentalPolicies());
            stmt.setInt(7, responses.getPerformanceMeasures());
            stmt.setInt(8, responses.getRiskAssessment());
            stmt.setInt(9, userId); // Vincula ao usuário
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o projectId gerado
                }
            }
        }
        throw new Exception("Erro ao cadastrar projeto.");
    }
}

