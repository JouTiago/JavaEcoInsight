package br.com.ecoinsight.dao;

import br.com.ecoinsight.exception.DatabaseException;
import br.com.ecoinsight.exception.ResourceNotFoundException;
import br.com.ecoinsight.model.DiagnosticoResponses;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ProjetoDao implements IProjetoDao {
    private final Connection connection;
    String tabela = "T_PROJETO";
    String tabela2 = "T_DIAGNOSTICO";

    ProjetoDao() {
        this.connection = ConnectionFactory.realizarConexao().getConnection();
    }

    @Override
    public int cadastrarProjeto(Projeto projeto, int userId) {
        String sql = "INSERT INTO "+ tabela +" (description, location, estimated_budget, planned_energy_types, " +
                "environmental_impact_knowledge, environmental_policies, performance_measures, risk_assessment, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"})) {
            System.out.println("Configurando parâmetros para o SQL...");

            // Configurando os parâmetros no PreparedStatement
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

            System.out.println("Executando a consulta...");
            stmt.executeUpdate();
            System.out.println("Consulta executada com sucesso.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1); // Obtém o ID diretamente
                    System.out.println("Projeto cadastrado com sucesso. ID gerado: " + generatedId);
                    return generatedId;
                } else {
                    throw new DatabaseException("Nenhum ID foi gerado para o projeto.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar o projeto no banco de dados.", e);
        }
    }



    @Override
    public List<Projeto> listarProjetosPorUsuario(int userId) {
        String sql = "SELECT id, description, location, estimated_budget, planned_energy_types, " +
                "environmental_impact_knowledge, environmental_policies, performance_measures, risk_assessment " +
                "FROM " + tabela + " WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Projeto> projetos = new ArrayList<>();
            while (rs.next()) {
                DiagnosticoResponses diagnostico = new DiagnosticoResponses(
                        rs.getInt("environmental_impact_knowledge"),
                        rs.getInt("environmental_policies"),
                        rs.getInt("performance_measures"),
                        rs.getInt("risk_assessment")
                );

                Projeto projeto = new Projeto(
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getInt("estimated_budget"),
                        List.of(rs.getString("planned_energy_types").split(",")),
                        diagnostico
                );
                projeto.setId(rs.getInt("id"));
                projetos.add(projeto);
            }

            if (projetos.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum projeto encontrado para o usuário com ID " + userId);
            }

            return projetos;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar projetos do usuário.", e);
        }
    }


    @Override
    public Projeto obterProjetoPorId(int projectId, int userId) {
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
            } else {
                throw new ResourceNotFoundException("Projeto com ID " + projectId + " não encontrado para o usuário "
                        + userId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao obter projeto do banco de dados.", e);
        }
    }

    @Override
    public boolean editarProjeto(int projectId, int userId, Projeto projeto) {
        StringBuilder sql = new StringBuilder("UPDATE " + tabela + " SET ");
        List<Object> params = new ArrayList<>();

        if (projeto.getDescription() != null) {
            sql.append("description = ?, ");
            params.add(projeto.getDescription());
        }
        if (projeto.getLocation() != null) {
            sql.append("location = ?, ");
            params.add(projeto.getLocation());
        }
        if (projeto.getEstimatedBudget() > 0) {
            sql.append("estimated_budget = ?, ");
            params.add(projeto.getEstimatedBudget());
        }
        if (projeto.getPlannedEnergyTypes() != null && !projeto.getPlannedEnergyTypes().isEmpty()) {
            sql.append("planned_energy_types = ?, ");
            params.add(String.join(",", projeto.getPlannedEnergyTypes()));
        }
        if (projeto.getDiagnosticResponses() != null) {
            DiagnosticoResponses respostas = projeto.getDiagnosticResponses();
            sql.append("environmental_impact_knowledge = ?, environmental_policies = ?, ");
            sql.append("performance_measures = ?, risk_assessment = ?, ");
            params.add(respostas.getEnvironmentalImpactKnowledge());
            params.add(respostas.getEnvironmentalPolicies());
            params.add(respostas.getPerformanceMeasures());
            params.add(respostas.getRiskAssessment());
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ? AND user_id = ?");
        params.add(projectId);
        params.add(userId);

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Projeto com ID " + projectId + " não encontrado para edição.");
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao editar projeto no banco de dados.", e);
        }
    }

    @Override
    public boolean excluirProjeto(int projectId, int userId) {
        String sql = "DELETE FROM " + tabela + " WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Projeto com ID " + projectId + " não encontrado para exclusão.");
            }
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir projeto do banco de dados.", e);
        }
    }

    @Override
    public boolean verificarDependencias(int projectId) {
        String sql = "SELECT COUNT(*) AS dependencias FROM " + tabela2 + " WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("dependencias") > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar dependências do projeto.", e);
        }
    }
}
