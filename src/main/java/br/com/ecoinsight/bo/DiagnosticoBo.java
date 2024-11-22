package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IDiagnosticoDao;
import br.com.ecoinsight.exception.*;
import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.PythonApiClient;

class DiagnosticoBo implements IDiagnosticoBo {
    private final IDiagnosticoDao diagnosticDao;

    DiagnosticoBo(IDiagnosticoDao diagnosticDao) {
        this.diagnosticDao = diagnosticDao;
    }

    @Override
    public Diagnostico iniciarDiagnostico(int projectId, Projeto projeto) {
        try {
            if (!projeto.isDiagnosticoCompleto()) {
                throw new ValidationException("Diagnóstico incompleto. Todas as perguntas devem ser respondidas.");
            }
            String llmAnalysis;
            try {
                llmAnalysis = PythonApiClient.classifyProject(projeto);
            } catch (Exception e) {
                throw new ExternalServiceException("Erro ao classificar o projeto usando o serviço Python.", e);
            }

            Diagnostico diagnostico;
            try {
                diagnostico = PythonApiClient.calculateSustainability(projeto, llmAnalysis);
            } catch (Exception e) {
                throw new ExternalServiceException("Erro ao calcular sustentabilidade usando o serviço Python.", e);
            }

            diagnostico.setProjectId(projectId);
            diagnosticDao.saveDiagnostic(diagnostico);

            return diagnostico;
        } catch (ValidationException | ExternalServiceException e) {
            throw e;
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao salvar o diagnóstico no banco de dados.", e);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro inesperado ao iniciar diagnóstico.", e);
        }
    }

    @Override
    public Diagnostico obterDiagnostico(int projectId) {
        try {
            Diagnostico diagnostico = diagnosticDao.getDiagnosticByProjectId(projectId);

            if (diagnostico == null) {
                throw new ResourceNotFoundException("Diagnóstico não encontrado para o projeto com ID " + projectId);
            }
            return diagnostico;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao buscar diagnóstico no banco de dados.", e);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro inesperado ao obter diagnóstico.", e);
        }
    }
}
