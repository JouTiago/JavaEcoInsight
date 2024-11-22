package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IProjetoBo;
import br.com.ecoinsight.bo.ProjetoBoFactory;
import br.com.ecoinsight.exception.*;
import br.com.ecoinsight.model.Projeto;
import br.com.ecoinsight.util.JWTUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("projectId", projectId))
                    .build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao cadastrar projeto."))
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProjetos(@HeaderParam("Authorization") String token) {
        try {
            int userId = JWTUtil.extrairUserId(token);
            List<Projeto> projetos = projetoBo.listarProjetosPorUsuario(userId);
            return Response.ok(projetos).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao listar projetos."))
                    .build();
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
                throw new ResourceNotFoundException("Projeto", projectId);
            }
            return Response.ok(projeto).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao obter projeto."))
                    .build();
        }
    }

    @PUT
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarProjeto(@HeaderParam("Authorization") String token,
                                  @PathParam("projectId") int projectId,
                                  Projeto projeto) {
        try {
            int userId = JWTUtil.extrairUserId(token);
            boolean atualizado = projetoBo.editarProjeto(projectId, userId, projeto);

            if (atualizado) {
                return Response.ok(Map.of("message", "Projeto atualizado com sucesso.")).build();
            }
            throw new ResourceNotFoundException("Projeto", projectId);
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao atualizar projeto."))
                    .build();
        }
    }

    @DELETE
    @Path("/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirProjeto(@HeaderParam("Authorization") String token,
                                   @PathParam("projectId") int projectId) {
        try {
            int userId = JWTUtil.extrairUserId(token);
            boolean excluido = projetoBo.excluirProjeto(projectId, userId);
            if (excluido) {
                return Response.noContent().build();
            }
            throw new ResourceNotFoundException("Projeto", projectId);
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao excluir projeto."))
                    .build();
        }
    }
}
