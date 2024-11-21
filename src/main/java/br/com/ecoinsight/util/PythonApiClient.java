package br.com.ecoinsight.util;

import br.com.ecoinsight.model.Diagnostic;
import br.com.ecoinsight.model.Projeto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PythonApiClient {
    private static final String PYTHON_API_BASE_URL = "http://python-api:5000";

    public static String classifyProject(Projeto projeto) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(PYTHON_API_BASE_URL + "/classify_project")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(projeto));

        if (response.getStatus() != 200) {
            throw new Exception("Erro ao classificar projeto.");
        }

        return response.readEntity(String.class);
    }

    public static Diagnostic calculateSustainability(Projeto projeto, String llmAnalysis) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(PYTHON_API_BASE_URL + "/calculate_sustainability")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new DiagnosticPayload(projeto, llmAnalysis)));

        if (response.getStatus() != 200) {
            throw new Exception("Erro ao calcular sustentabilidade.");
        }

        return response.readEntity(Diagnostic.class);
    }

    private static class DiagnosticPayload {
        public Projeto project;
        public String llmResponse;

        DiagnosticPayload(Projeto project, String llmResponse) {
            this.project = project;
            this.llmResponse = llmResponse;
        }
    }
}
