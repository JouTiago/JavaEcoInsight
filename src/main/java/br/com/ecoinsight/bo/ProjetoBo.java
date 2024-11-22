package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IProjetoDao;
import br.com.ecoinsight.exception.*;
import br.com.ecoinsight.model.DiagnosticoResponses;
import br.com.ecoinsight.model.Projeto;

import java.util.ArrayList;
import java.util.List;

class ProjetoBo implements IProjetoBo {
    private final IProjetoDao projetoDao;

    ProjetoBo(IProjetoDao projetoDao) {
        this.projetoDao = projetoDao;
    }

    @Override
    public int cadastrarProjeto(Projeto projeto, int userId) {
        try {
            if (projeto.getDescription() == null || projeto.getDescription().isEmpty()) {
                throw new ValidationException("A descrição do projeto é obrigatória.");
            }
            if (projeto.getLocation() == null || projeto.getLocation().isEmpty()) {
                throw new ValidationException("A localização do projeto é obrigatória.");
            }
            if (projeto.getEstimatedBudget() <= 0) {
                throw new ValidationException("O orçamento estimado deve ser maior que zero.");
            }
            if (projeto.getPlannedEnergyTypes() == null || projeto.getPlannedEnergyTypes().isEmpty()) {
                throw new ValidationException("É necessário fornecer pelo menos um tipo de energia planejado.");
            }

            DiagnosticoResponses respostas = projeto.getDiagnosticResponses();
            if (respostas == null) {
                throw new ValidationException("É obrigatório responder todas as perguntas.");
            }

            return projetoDao.cadastrarProjeto(projeto, userId);
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao cadastrar projeto.", e);
        }
    }

    @Override
    public List<Projeto> listarProjetosPorUsuario(int userId) {
        try {
            if (userId <= 0) {
                throw new ValidationException("ID do usuário inválido.");
            }

            List<Projeto> projetos = projetoDao.listarProjetosPorUsuario(userId);

            if (projetos == null) {
                return new ArrayList<>();
            }

            for (Projeto projeto : projetos) {
                System.out.println("Projeto: " + projeto.getDescription());
                System.out.println("Custo por tipo de energia: " + projeto.calcularCustoPorTipo());
            }

            return projetos;
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao listar projetos do usuário.", e);
        }
    }

    @Override
    public Projeto obterProjetoPorId(int projectId, int userId) {
        try {
            if (projectId <= 0) {
                throw new ValidationException("ID do projeto inválido.");
            }
            if (userId <= 0) {
                throw new ValidationException("ID do usuário inválido.");
            }

            Projeto projeto = projetoDao.obterProjetoPorId(projectId, userId);

            if (projeto == null) {
                throw new ResourceNotFoundException("Projeto não encontrado ou não pertence ao usuário.");
            }

            return projeto;
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao buscar projeto pelo ID.", e);
        }
    }

    @Override
    public boolean editarProjeto(int projectId, int userId, Projeto projeto) {
        try {
            if (projetoDao.obterProjetoPorId(projectId, userId) == null) {
                throw new ResourceNotFoundException("Projeto não encontrado ou sem permissão.");
            }
            if (projeto.getEstimatedBudget() < 0) {
                throw new ValidationException("Orçamento inválido.");
            }
            return projetoDao.editarProjeto(projectId, userId, projeto);
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao editar projeto.", e);
        }
    }

    @Override
    public boolean excluirProjeto(int projectId, int userId) {
        try {
            // Validações dos IDs
            if (projectId <= 0) {
                throw new ValidationException("ID do projeto inválido.");
            }
            if (userId <= 0) {
                throw new ValidationException("ID do usuário inválido.");
            }

            // Verifica de projeto existe e se existem dependências associadas
            Projeto projetoExistente = projetoDao.obterProjetoPorId(projectId, userId);
            if (projetoExistente == null) {
                throw new ResourceNotFoundException("Projeto não encontrado ou não pertence ao usuário.");
            }

            if (projetoDao.verificarDependencias(projectId)) {
                throw new DependencyException("O projeto possui dependências associadas e não pode ser excluído.");
            }

            return projetoDao.excluirProjeto(projectId, userId);
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao excluir projeto.", e);
        }
    }
}
