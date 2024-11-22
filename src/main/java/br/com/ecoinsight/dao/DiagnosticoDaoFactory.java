package br.com.ecoinsight.dao;

public class DiagnosticoDaoFactory {
    public static IDiagnosticoDao criarDiagnosticDao() {
        return new DiagnosticoDao();
    }
}
