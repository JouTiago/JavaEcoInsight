package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IProjetoDao;
import br.com.ecoinsight.dao.ProjetoDaoFactory;

public class ProjetoBoFactory {
    public static IProjetoBo criarProjetoBo() {
        IProjetoDao projetoDao = ProjetoDaoFactory.criarProjetoDao();
        return new ProjetoBo(projetoDao);
    }
}
