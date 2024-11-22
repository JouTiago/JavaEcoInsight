package br.com.ecoinsight.model;

public class DiagnosticoResponses {
    private int environmentalImpactKnowledge;
    private int environmentalPolicies;
    private int performanceMeasures;
    private int riskAssessment;

    public DiagnosticoResponses(int environmentalImpactKnowledge, int environmentalPolicies, int performanceMeasures,
                                int riskAssessment) {
        this.setEnvironmentalImpactKnowledge(environmentalImpactKnowledge);
        this.setEnvironmentalPolicies(environmentalPolicies);
        this.setPerformanceMeasures(performanceMeasures);
        this.setRiskAssessment(riskAssessment);
    }

    public int getEnvironmentalImpactKnowledge() {
        return environmentalImpactKnowledge;
    }

    public void setEnvironmentalImpactKnowledge(int environmentalImpactKnowledge) {
        this.environmentalImpactKnowledge = environmentalImpactKnowledge;
    }

    public int getEnvironmentalPolicies() {
        return environmentalPolicies;
    }

    public void setEnvironmentalPolicies(int environmentalPolicies) {
        this.environmentalPolicies = environmentalPolicies;
    }

    public int getPerformanceMeasures() {
        return performanceMeasures;
    }

    public void setPerformanceMeasures(int performanceMeasures) {
        this.performanceMeasures = performanceMeasures;
    }

    public int getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(int riskAssessment) {
        this.riskAssessment = riskAssessment;
    }
}
