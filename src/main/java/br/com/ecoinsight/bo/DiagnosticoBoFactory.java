package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.DiagnosticoDaoFactory;
import br.com.ecoinsight.dao.IDiagnosticoDao;

public class DiagnosticoBoFactory {
    public static IDiagnosticoBo criarDiagnosticBo() {
        IDiagnosticoDao diagnosticDao = DiagnosticoDaoFactory.criarDiagnosticDao();
        return new DiagnosticoBo(diagnosticDao);
    }
}
