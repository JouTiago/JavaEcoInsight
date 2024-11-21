package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Usuario;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IUsuarioBo {
    String login(String email, String senha) throws Exception;
    boolean cadastrar(Usuario usuario) throws Exception;
    boolean solicitarAlteracaoSenha(String email) throws Exception;
    boolean alterarSenha(String token, String novaSenha) throws Exception;
}
