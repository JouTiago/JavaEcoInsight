package br.com.ecoinsight.util;

import br.com.ecoinsight.exception.InternalServerErrorException;
import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PythonApiClient {
    private static final String PYTHON_API_BASE_URL = "http://127.0.0.1:5000";

    public static String classifyProject(Projeto projeto) {
        Client client = ClientBuilder.newClient();
        try (Response response = client.target(PYTHON_API_BASE_URL + "/classify_project")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(projeto))) {

            if (response.getStatus() != 200) {
                throw new InternalServerErrorException("Erro ao classificar projeto. Código de status: "
                        + response.getStatus());
            }

            return response.readEntity(String.class);
        }
    }

    public static Diagnostico calculateSustainability(Projeto projeto, String llmAnalysis) {
        Client client = ClientBuilder.newClient();
        try (Response response = client.target(PYTHON_API_BASE_URL + "/calculate_sustainability")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new DiagnosticarPayload(projeto, llmAnalysis)))) {

            if (response.getStatus() != 200) {
                throw new InternalServerErrorException("Erro ao calcular sustentabilidade. Código de status: "
                        + response.getStatus());
            }

            return response.readEntity(Diagnostico.class);
        }
    }

    private static class DiagnosticarPayload {
        public Projeto projeto;
        public String llmResponse;

        DiagnosticarPayload(Projeto projeto, String llmResponse) {
            this.projeto = projeto;
            this.llmResponse = llmResponse;
        }
    }
}
