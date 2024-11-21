package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Diagnostico;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IDiagnosticDao {
    void saveDiagnostic(Diagnostico diagnostico) throws Exception;
    Diagnostico getDiagnosticByProjectId(int projectId) throws Exception;
}
