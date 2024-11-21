package br.com.ecoinsight.dao;

public class UsuarioDaoFactory {

    public static IUsuarioDao criarUsuarioDao() {
        return new UsuarioDao();
    }
}