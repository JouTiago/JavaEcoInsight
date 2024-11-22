package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IDiagnosticoDao;
import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.PythonApiClient;

class DiagnosticBo implements IDiagnosticBo {
    private final IDiagnosticoDao diagnosticDao;

    DiagnosticBo(IDiagnosticoDao diagnosticDao) {
        this.diagnosticDao = diagnosticDao;
    }

    @Override
    public Diagnostico iniciarDiagnostico(int projectId, Projeto projeto) throws Exception {
        String llmAnalysis = PythonApiClient.classifyProject(projeto);

        Diagnostico diagnostico = PythonApiClient.calculateSustainability(projeto, llmAnalysis);

        diagnostico.setProjectId(projectId);
        diagnosticDao.saveDiagnostic(diagnostico);

        return diagnostico;
    }

    @Override
    public Diagnostico obterDiagnostico(int projectId) throws Exception {
        return diagnosticDao.getDiagnosticByProjectId(projectId);
    }
}
