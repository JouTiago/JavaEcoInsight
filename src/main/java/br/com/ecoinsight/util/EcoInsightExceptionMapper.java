package br.com.ecoinsight.util;

import br.com.ecoinsight.exception.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class EcoInsightExceptionMapper implements ExceptionMapper<EcoInsightException> {

    @Override
    public Response toResponse(EcoInsightException exception) {
        if (exception instanceof ValidationException) {
            return buildResponse(Response.Status.BAD_REQUEST, exception.getMessage());
        }
        if (exception instanceof UnauthorizedException) {
            return buildResponse(Response.Status.UNAUTHORIZED, exception.getMessage());
        }
        if (exception instanceof ResourceNotFoundException) {
            return buildResponse(Response.Status.NOT_FOUND, exception.getMessage());
        }
        if (exception instanceof DependencyException) {
            return buildResponse(Response.Status.CONFLICT, exception.getMessage());
        }
        if (exception instanceof DatabaseException) {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Erro no banco de dados: "
                    + exception.getMessage());
        }
        if (exception instanceof InternalServerErrorException) {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Erro interno no servidor: "
                    + exception.getMessage());
        }
        return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Erro desconhecido.");
    }

    private Response buildResponse(Response.Status status, String message) {
        return Response.status(status)
                .entity(Map.of("error", message))
                .build();
    }
}
