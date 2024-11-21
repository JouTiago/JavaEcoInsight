package br.com.ecoinsight.model;

import java.util.List;

public class Projeto {
    private String description;
    private String location;
    private int estimatedBudget;
    private List<String> plannedEnergyTypes;
    private DiagnosticResponses diagnosticResponses;

    public Projeto(String description, String location, int estimatedBudget, List<String> plannedEnergyTypes, DiagnosticResponses diagnosticResponses) {
        this.description = description;
        this.location = location;
        this.estimatedBudget = estimatedBudget;
        this.plannedEnergyTypes = plannedEnergyTypes;
        this.diagnosticResponses = diagnosticResponses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(int estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public List<String> getPlannedEnergyTypes() {
        return plannedEnergyTypes;
    }

    public void setPlannedEnergyTypes(List<String> plannedEnergyTypes) {
        this.plannedEnergyTypes = plannedEnergyTypes;
    }

    public DiagnosticResponses getDiagnosticResponses() {
        return diagnosticResponses;
    }

    public void setDiagnosticResponses(DiagnosticResponses diagnosticResponses) {
        this.diagnosticResponses = diagnosticResponses;
    }
}
