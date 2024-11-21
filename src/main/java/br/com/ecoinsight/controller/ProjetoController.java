package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IProjetoBo;
import br.com.ecoinsight.bo.ProjetoBoFactory;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.JWTUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/projects")
public class ProjetoController {
    private final IProjetoBo projetoBo = ProjetoBoFactory.criarProjetoBo();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarProjeto(@HeaderParam("Authorization") String token, Projeto projeto) {
        try {
            int userId = JWTUtil.extrairUserId(token);

            int projectId = projetoBo.cadastrarProjeto(projeto, userId);

            return Response.ok().entity("{\"projectId\": " + projectId + "}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
