package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IDiagnosticBo;
import br.com.ecoinsight.bo.DiagnosticBoFactory;
import br.com.ecoinsight.model.Diagnostic;
import br.com.ecoinsight.model.Projeto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/projects/{projectId}/diagnostic")
public class DiagnosticController {
    private final IDiagnosticBo diagnosticBo = DiagnosticBoFactory.criarDiagnosticBo();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarDiagnostico(@PathParam("projectId") int projectId, Projeto projeto) {
        try {
            Diagnostic diagnostic = diagnosticBo.iniciarDiagnostico(projectId, projeto);
            return Response.ok(diagnostic).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterDiagnostico(@PathParam("projectId") int projectId) {
        try {
            Diagnostic diagnostic = diagnosticBo.obterDiagnostico(projectId);
            if (diagnostic == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Diagnóstico não encontrado.\"}").build();
            }
            return Response.ok(diagnostic).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
