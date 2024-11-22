package br.com.ecoinsight.model;

import br.com.ecoinsight.exception.ValidationException;

import java.util.List;

public class Projeto {
    private int id;
    private String description;
    private String location;
    private int estimatedBudget;
    private List<String> plannedEnergyTypes;
    private DiagnosticoResponses diagnosticoResponses;

    public Projeto(int id, String description, String location, int estimatedBudget, List<String> plannedEnergyTypes,
                   DiagnosticoResponses diagnosticoResponses) {
        this.setId(id);
        this.setDescription(description);
        this.setLocation(location);
        this.setEstimatedBudget(estimatedBudget);
        this.setPlannedEnergyTypes(plannedEnergyTypes);
        this.diagnosticoResponses = diagnosticoResponses;
    }

    public Projeto(int id, String description, String location, int estimatedBudget, List<String> plannedEnergyTypes) {
        setId(id);
        setDescription(description);
        setLocation(location);
        setEstimatedBudget(estimatedBudget);
        setPlannedEnergyTypes(plannedEnergyTypes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new ValidationException("O ID não pode ser negativo.");
        }
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("A descrição não pode ser vazia.");
        }
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new ValidationException("A localização não pode ser vazia.");
        }
        this.location = location;
    }

    public int getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(int estimatedBudget) {
        if (estimatedBudget <= 0) {
            throw new ValidationException("O orçamento estimado deve ser maior que zero.");
        }
        this.estimatedBudget = estimatedBudget;
    }

    public List<String> getPlannedEnergyTypes() {
        return plannedEnergyTypes;
    }

    public void setPlannedEnergyTypes(List<String> plannedEnergyTypes) {
        if (plannedEnergyTypes == null || plannedEnergyTypes.isEmpty()) {
            throw new ValidationException("Deve haver ao menos um tipo de energia planejado.");
        }
        this.plannedEnergyTypes = plannedEnergyTypes;
    }

    public DiagnosticoResponses getDiagnosticResponses() {
        return diagnosticoResponses;
    }

    public void setDiagnosticResponses(DiagnosticoResponses diagnosticoResponses) {
        this.diagnosticoResponses = diagnosticoResponses;
    }

    public double calcularCustoPorTipo() {
        if (plannedEnergyTypes == null || plannedEnergyTypes.isEmpty()) {
            throw new ValidationException("Não é possível calcular o custo sem tipos de energia planejados.");
        }
        return estimatedBudget / (double) plannedEnergyTypes.size();
    }

    public boolean isDiagnosticoCompleto() {
        if (diagnosticoResponses == null) {
            return false;
        }
        return diagnosticoResponses.getEnvironmentalImpactKnowledge() > 0 &&
                diagnosticoResponses.getEnvironmentalPolicies() > 0 &&
                diagnosticoResponses.getPerformanceMeasures() > 0 &&
                diagnosticoResponses.getRiskAssessment() > 0;
    }
}
