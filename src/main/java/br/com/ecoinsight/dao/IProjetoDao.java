package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Projeto;

public interface IProjetoDao {
    int cadastrarProjeto(Projeto projeto, int userId) throws Exception; // Adicionado userId
}
