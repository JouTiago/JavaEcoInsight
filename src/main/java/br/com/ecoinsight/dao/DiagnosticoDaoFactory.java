package br.com.ecoinsight.dao;

public class DiagnosticDaoFactory {
    public static IDiagnosticDao criarDiagnosticDao() {
        return new DiagnosticDao();
    }
}
