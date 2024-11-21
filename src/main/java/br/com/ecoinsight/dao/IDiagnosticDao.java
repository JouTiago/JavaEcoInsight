package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Diagnostic;

public interface IDiagnosticDao {
    void saveDiagnostic(Diagnostic diagnostic) throws Exception;
    Diagnostic getDiagnosticByProjectId(int projectId) throws Exception;
}
