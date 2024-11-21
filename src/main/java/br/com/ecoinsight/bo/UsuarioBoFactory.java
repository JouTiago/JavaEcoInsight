package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.UsuarioDaoFactory;

public class UsuarioBoFactory {

    public static IUsuarioBo criarUsuarioBo() {
        return new UsuarioBo(UsuarioDaoFactory.criarUsuarioDao());
    }
}
