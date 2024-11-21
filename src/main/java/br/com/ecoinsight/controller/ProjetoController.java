package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IProjetoBo;
import br.com.ecoinsight.bo.ProjetoBoFactory;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.JWTUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProjetos(@HeaderParam("Authorization") String token) {
        try {
            int userId = JWTUtil.extrairUserId(token);
            List<Projeto> projetos = projetoBo.listarProjetosPorUsuario(userId);
            return Response.ok(projetos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterProjetoPorId(@HeaderParam("Authorization") String token, @PathParam("projectId") int projectId) {
        try {
            int userId = JWTUtil.extrairUserId(token);
            Projeto projeto = projetoBo.obterProjetoPorId(projectId, userId);
            if (projeto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Projeto não encontrado.\"}").build();
            }
            return Response.ok(projeto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
