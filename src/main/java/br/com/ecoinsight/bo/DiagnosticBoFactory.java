package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.DiagnosticDaoFactory;
import br.com.ecoinsight.dao.IDiagnosticDao;

public class DiagnosticBoFactory {
    public static IDiagnosticBo criarDiagnosticBo() {
        IDiagnosticDao diagnosticDao = DiagnosticDaoFactory.criarDiagnosticDao();
        return new DiagnosticBo(diagnosticDao);
    }
}
