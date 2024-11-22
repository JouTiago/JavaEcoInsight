package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IDiagnosticoBo;
import br.com.ecoinsight.bo.DiagnosticoBoFactory;
import br.com.ecoinsight.exception.*;
import br.com.ecoinsight.model.Diagnostico;
import br.com.ecoinsight.model.Projeto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/projects/{projectId}/diagnostic")
public class DiagnosticoController {
    private final IDiagnosticoBo diagnosticBo = DiagnosticoBoFactory.criarDiagnosticBo();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarDiagnostico(@PathParam("projectId") int projectId, Projeto projeto) {
        try {
            Diagnostico diagnostico = diagnosticBo.iniciarDiagnostico(projectId, projeto);
            return Response.status(Response.Status.CREATED).entity(diagnostico).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao iniciar o diagnóstico."))
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterDiagnostico(@PathParam("projectId") int projectId) {
        try {
            Diagnostico diagnostico = diagnosticBo.obterDiagnostico(projectId);
            if (diagnostico == null) {
                throw new ResourceNotFoundException("Diagnóstico", projectId);
            }
            return Response.ok(diagnostico).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao obter o diagnóstico."))
                    .build();
        }
    }
}
