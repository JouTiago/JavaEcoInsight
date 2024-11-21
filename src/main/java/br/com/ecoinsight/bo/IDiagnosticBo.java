package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Diagnostic;
import br.com.ecoinsight.model.Projeto;

public interface IDiagnosticBo {
    Diagnostic iniciarDiagnostico(int projectId, Projeto projeto) throws Exception;
    Diagnostic obterDiagnostico(int projectId) throws Exception;
}
