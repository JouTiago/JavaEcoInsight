package br.com.ecoinsight.exception;

public class DependencyException extends EcoInsightException {
    public DependencyException(String dependency) {
        super("Não é possível concluir a ação devido a dependências existentes: " + dependency, 409);
    }
}
