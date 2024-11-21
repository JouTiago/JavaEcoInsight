package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.DiagnosticoResponses;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            DiagnosticoResponses responses = projeto.getDiagnosticResponses();
            stmt.setInt(5, responses.getEnvironmentalImpactKnowledge());
            stmt.setInt(6, responses.getEnvironmentalPolicies());
            stmt.setInt(7, responses.getPerformanceMeasures());
            stmt.setInt(8, responses.getRiskAssessment());
            stmt.setInt(9, userId);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new Exception("Erro ao cadastrar projeto.");
    }

    @Override
    public List<Projeto> listarProjetosPorUsuario(int userId) throws Exception {
        String sql = "SELECT id, description, location, estimated_budget, planned_energy_types " +
                "FROM " + tabela + " WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Projeto> projetos = new ArrayList<>();
            while (rs.next()) {
                Projeto projeto = new Projeto(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getInt("estimated_budget"),
                    List.of(rs.getString("planned_energy_types").split(","))
                );
                projetos.add(projeto);
            }
            return projetos;
        }
    }


    @Override
    public Projeto obterProjetoPorId(int projectId, int userId) throws Exception {
        String sql = "SELECT * FROM " + tabela + " WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Projeto(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getInt("estimated_budget"),
                    List.of(rs.getString("planned_energy_types").split(",")),
                    new DiagnosticoResponses(
                        rs.getInt("environmental_impact_knowledge"),
                        rs.getInt("environmental_policies"),
                        rs.getInt("performance_measures"),
                        rs.getInt("risk_assessment")
                    )
                );
            }
        }
        return null;
    }
}

