package br.com.ecoinsight.exception;

public class DatabaseException extends EcoInsightException {

    public DatabaseException(String message, Throwable cause) {
        super(message, 500, cause);
    }

    public DatabaseException(String message) {
        super(message, 500);
    }
}
