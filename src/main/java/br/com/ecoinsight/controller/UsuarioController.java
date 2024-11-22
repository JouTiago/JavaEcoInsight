package br.com.ecoinsight.controller;

import br.com.ecoinsight.bo.IUsuarioBo;
import br.com.ecoinsight.bo.UsuarioBoFactory;
import br.com.ecoinsight.exception.ValidationException;
import br.com.ecoinsight.exception.UnauthorizedException;
import br.com.ecoinsight.model.Usuario;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/auth")
public class UsuarioController {

    private final IUsuarioBo usuarioBo = UsuarioBoFactory.criarUsuarioBo();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> credentials) {
        try {
            String token = usuarioBo.login(credentials.get("email"), credentials.get("password"));
            return Response.ok(Map.of("token", token)).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao realizar login."))
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Usuario usuario) {
        try {
            usuarioBo.cadastrar(usuario);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("message", "Usuário registrado com sucesso!"))
                    .build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao registrar usuário."))
                    .build();
        }
    }

    @POST
    @Path("/request-password-reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestPasswordReset(Map<String, String> payload) {
        try {
            usuarioBo.solicitarAlteracaoSenha(payload.get("email"));
            return Response.ok(Map.of("message", "Link de redefinição enviado!")).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Erro inesperado ao solicitar redefinição de senha."))
                    .build();
        }
    }

    @POST
    @Path("/reset-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetPassword(Map<String, String> payload) {
        try {
            usuarioBo.alterarSenha(payload.get("token"), payload.get("newPassword"));
            return Response.ok(Map.of("message", "Senha redefinida com sucesso!")).build();
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
                    .entity(Map.of("error", "Erro inesperado ao redefinir senha."))
                    .build();
        }
    }
}
