package br.com.ecoinsight.exception;

public class UnauthorizedException extends EcoInsightException {
    public UnauthorizedException(String message) {
        super(message, 401);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, 401);
        initCause(cause);
    }
}
