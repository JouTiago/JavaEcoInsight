package br.com.ecoinsight.exception;

public class InternalServerErrorException extends EcoInsightException {
    public InternalServerErrorException(String message) {
        super(message, 500);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, 500);
        initCause(cause);
    }
}
