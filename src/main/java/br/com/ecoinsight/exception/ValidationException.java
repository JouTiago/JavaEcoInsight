package br.com.ecoinsight.exception;

public class ValidationException extends EcoInsightException {
    public ValidationException(String message) {
        super(message, 400);
    }
}
