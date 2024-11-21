package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Projeto;

public interface IProjetoBo {
    int cadastrarProjeto(Projeto projeto, int userId) throws Exception; // Adicionado userId
}
