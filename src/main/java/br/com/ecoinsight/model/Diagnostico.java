package br.com.ecoinsight.model;

public class Diagnostico {
    private int projectId;
    private String llmAnalysis;
    private double sustainabilityScore;
    private String llmJustification;

    public Diagnostico(int projectId, String llmAnalysis, double sustainabilityScore, String llmJustification) {
        this.projectId = projectId;
        this.llmAnalysis = llmAnalysis;
        this.sustainabilityScore = sustainabilityScore;
        this.llmJustification = llmJustification;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getLlmAnalysis() {
        return llmAnalysis;
    }

    public void setLlmAnalysis(String llmAnalysis) {
        this.llmAnalysis = llmAnalysis;
    }

    public double getSustainabilityScore() {
        return sustainabilityScore;
    }

    public void setSustainabilityScore(double sustainabilityScore) {
        this.sustainabilityScore = sustainabilityScore;
    }

    public String getLlmJustification() {
        return llmJustification;
    }

    public void setLlmJustification(String llmJustification) {
        this.llmJustification = llmJustification;
    }
}
