package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.DiagnosticoDaoFactory;
import br.com.ecoinsight.dao.IDiagnosticoDao;

public class DiagnosticBoFactory {
    public static IDiagnosticBo criarDiagnosticBo() {
        IDiagnosticoDao diagnosticDao = DiagnosticoDaoFactory.criarDiagnosticDao();
        return new DiagnosticBo(diagnosticDao);
    }
}
