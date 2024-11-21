package br.com.ecoinsight.model;

import java.util.List;

public class Projeto {
    private int id;
    private String description;
    private String location;
    private int estimatedBudget;
    private List<String> plannedEnergyTypes;
    private DiagnosticoResponses diagnosticoResponses;

    public Projeto(int id, String description, String location, int estimatedBudget, List<String> plannedEnergyTypes, DiagnosticoResponses diagnosticoResponses) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.estimatedBudget = estimatedBudget;
        this.plannedEnergyTypes = plannedEnergyTypes;
        this.diagnosticoResponses = diagnosticoResponses;
    }

    public Projeto(int id, String description, String location, int estimatedBudget, List<String> plannedEnergyTypes) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.estimatedBudget = estimatedBudget;
        this.plannedEnergyTypes = plannedEnergyTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public DiagnosticoResponses getDiagnosticResponses() {
        return diagnosticoResponses;
    }

    public void setDiagnosticResponses(DiagnosticoResponses diagnosticoResponses) {
        this.diagnosticoResponses = diagnosticoResponses;
    }
}
