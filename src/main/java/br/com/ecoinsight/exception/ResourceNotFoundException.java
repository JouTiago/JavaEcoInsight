package br.com.ecoinsight.exception;

public class ResourceNotFoundException extends EcoInsightException {
    public ResourceNotFoundException(String resourceName, int id) {
        super(resourceName + " com ID " + id + " não foi encontrado.", 404);
    }

    public ResourceNotFoundException(String message) {
        super(message, 404);
    }
}
