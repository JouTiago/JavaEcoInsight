package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Usuario;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IUsuarioBo {
    String login(String email, String senha);
    void cadastrar(Usuario usuario);
    void solicitarAlteracaoSenha(String email);
    void alterarSenha(String token, String novaSenha);
}
