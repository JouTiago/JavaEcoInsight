package br.com.ecoinsight.exception;

public class ExternalServiceException extends ApiException {
    public ExternalServiceException(String message) {
        super(message, 500);
    }

    public ExternalServiceException(String message, Throwable cause) {
        super(message, 500, cause);
    }
}
