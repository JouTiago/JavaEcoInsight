package br.com.ecoinsight.exception;

public abstract class EcoInsightException extends RuntimeException {
    private final int statusCode;

    public EcoInsightException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public EcoInsightException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
