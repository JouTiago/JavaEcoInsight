package br.com.ecoinsight.dao;

public class ProjetoDaoFactory {
    public static IProjetoDao criarProjetoDao() {
        return new ProjetoDao();
    }
}
