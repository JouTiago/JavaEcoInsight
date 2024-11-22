package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Diagnostico;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IDiagnosticoDao {
    void saveDiagnostic(Diagnostico diagnostico);
    Diagnostico getDiagnosticByProjectId(int projectId);
}
