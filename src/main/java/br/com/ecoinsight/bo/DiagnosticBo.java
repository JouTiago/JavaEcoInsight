package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IDiagnosticDao;
import br.com.ecoinsight.model.Diagnostic;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.PythonApiClient;

class DiagnosticBo implements IDiagnosticBo {
    private final IDiagnosticDao diagnosticDao;

    DiagnosticBo(IDiagnosticDao diagnosticDao) {
        this.diagnosticDao = diagnosticDao;
    }

    @Override
    public Diagnostic iniciarDiagnostico(int projectId, Projeto projeto) throws Exception {
        String llmAnalysis = PythonApiClient.classifyProject(projeto);

        Diagnostic diagnostic = PythonApiClient.calculateSustainability(projeto, llmAnalysis);

        diagnostic.setProjectId(projectId);
        diagnosticDao.saveDiagnostic(diagnostic);

        return diagnostic;
    }

    @Override
    public Diagnostic obterDiagnostico(int projectId) throws Exception {
        return diagnosticDao.getDiagnosticByProjectId(projectId);
    }
}
