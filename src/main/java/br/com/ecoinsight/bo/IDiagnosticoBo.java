package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IDiagnosticoBo {
    Diagnostico iniciarDiagnostico(int projectId, Projeto projeto);
    Diagnostico obterDiagnostico(int projectId);
}
