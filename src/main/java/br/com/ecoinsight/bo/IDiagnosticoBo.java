package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IDiagnosticBo {
    Diagnostico iniciarDiagnostico(int projectId, Projeto projeto) throws Exception;
    Diagnostico obterDiagnostico(int projectId) throws Exception;
}
