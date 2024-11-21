package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IProjetoDao;
import br.com.ecoinsight.model.Projeto;

class ProjetoBo implements IProjetoBo { // Package-private
    private final IProjetoDao projetoDao;

    ProjetoBo(IProjetoDao projetoDao) {
        this.projetoDao = projetoDao;
    }

    @Override
    public int cadastrarProjeto(Projeto projeto, int userId) throws Exception {
        return projetoDao.cadastrarProjeto(projeto, userId);
    }
}
