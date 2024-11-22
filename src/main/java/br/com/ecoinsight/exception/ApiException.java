package br.com.ecoinsight.exception;

public class ApiException extends EcoInsightException {
    public ApiException(String message, int statusCode) {
        super(message, statusCode);
    }

    public ApiException(String message, int statusCode, Throwable cause) {
        super(message, statusCode);
        initCause(cause);
    }
}
